import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { Button, Notification, TextField } from '@vaadin/react-components';
import { useAuth } from "react-oidc-context";

export const config: ViewConfig = {
  menu: { order: 1, icon: 'line-awesome/svg/universal-access-solid.svg' },
  title: 'Login',
};

export default function LoginView() {
  const auth = useAuth();
  const name = useSignal('');

   if (auth.isLoading) return <div>Cargando...</div>;
    if (auth.error) return <div>Error: {auth.error.message}</div>;

    if (auth.isAuthenticated) {
      return <div>Ya has iniciado sesión como {auth.user?.profile.email}</div>;
    }

    return (
      <div>
        <Button onClick={() => auth.signinRedirect()}>Iniciar sesión con Cognito</Button>
      </div>
    );
  }
