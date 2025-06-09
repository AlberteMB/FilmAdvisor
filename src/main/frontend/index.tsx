import React from 'react';
import ReactDOM from 'react-dom/client';
import { AuthProvider } from 'react-oidc-context';
import { RouterProvider } from 'react-router-dom';
import { router } from '@vaadin/hilla-file-router';

const oidcConfig = {
  authority: "https://eu-central-1e1deargct.auth.eu-central-1.amazoncognito.com",
  client_id: "4rsgg7f0jof8062a9rh1u4mgcl",
  redirect_uri: "http://localhost:5173/",
  response_type: 'code',
  scope: 'openid profile email',
};

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <AuthProvider {...oidcConfig}>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>
);