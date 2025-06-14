import { createMenuItems, useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import { effect, signal } from '@vaadin/hilla-react-signals';
import { AppLayout, DrawerToggle, Icon, SideNav, SideNavItem, Button } from '@vaadin/react-components';
import { Suspense, useEffect } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import  MovieFilter  from '../components/MovieFilter';
import { FilterProvider } from '../context/FilterContext';
import { useAuth } from "react-oidc-context";

const documentTitleSignal = signal('');
effect(() => {
  document.title = documentTitleSignal.value;
});

// Publish for Vaadin to use
(window as any).Vaadin.documentTitleSignal = documentTitleSignal;

export default function MainLayout() {
  const currentTitle = useViewConfig()?.title;
  const navigate = useNavigate();
  const location = useLocation();
  const auth = useAuth();

   const cognitoDomain = "https://eu-central-1e1deargct.auth.eu-central-1.amazoncognito.com";
   const clientId = "4rsgg7f0jof8062a9rh1u4mgcl";
   const logoutUri = "http://localhost:8080/";

   function handleLogout() {
       auth.removeUser?.().then(() => {
           window.location.href = `${cognitoDomain}/logout?client_id=${clientId}&logout_uri=${encodeURIComponent(logoutUri)}`;
         });
   }

      return <div>Cargando autenticación...</div>;
    }

    // If there is an error
    if (auth.error) {
      return <div>Error de autenticación: {auth.error.message}</div>;
    }

    // Not authenticated
    if (!auth.isAuthenticated) {
      auth.signinRedirect();
      return null;
    }

  useEffect(() => {
    if (currentTitle) {
      documentTitleSignal.value = currentTitle;
    }
  }, [currentTitle]);

  return (
  <FilterProvider>
    <AppLayout primarySection="drawer">
      <div slot="drawer" className="flex flex-col justify-between h-full p-m">
        <header className="flex flex-col gap-m">
          <span className="font-semibold text-l">Film Advisor</span>
          <SideNav onNavigate={({ path }) => navigate(path!)} location={location}>
            {createMenuItems().map(({ to, title, icon }) => (
              <SideNavItem path={to} key={to}>
                {icon ? <Icon src={icon} slot="prefix"></Icon> : <></>}
                {title}
                {auth.isAuthenticated && (
                          <Button onClick={handleLogout}>Cerrar sesión</Button>
                        )}
              </SideNavItem>
            ))}
          </SideNav>
            <MovieFilter/>
        </header>
      </div>
      <DrawerToggle slot="navbar" aria-label="Menu toggle"></DrawerToggle>
      <h1 slot="navbar" className="text-l m-0">
        {documentTitleSignal}
      </h1>
      <Suspense>
        <Outlet />
      </Suspense>
    </AppLayout>
  </FilterProvider>
  );
}
