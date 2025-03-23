import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { Button, Notification, TextField } from '@vaadin/react-components';


export const config: ViewConfig = {
  menu: { order: 1, icon: 'line-awesome/svg/universal-access-solid.svg' },
  title: 'Login',
};

export default function LoginView() {
  const name = useSignal('');

  return (
    <>
      <section className="flex p-m gap-m items-end">
        <TextField
          label="Your name"
          onValueChanged={(e) => {
            name.value = e.detail.value;
          }}
        />

      </section>
    </>
  );
}
