<script>
  import IconText from "$lib/IconText.svelte";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/2-register.css";

  import Input from "$lib/Input.svelte";
  import { InputTypes } from "$lib/InputTypes";
  import { USERS_LIST_MOCK } from "$lib/Users_Mock";
  import { User } from "$lib/User";

  let emailRegisterValue: string = "";
  let passwordRegister: string = "";
  let passwordRetry: string = "";
  let registerMessage: string;
  let errorMessage1st: string;
  let errorMessage2nd: string;

  console.log(USERS_LIST_MOCK);

  function handleRegistration(event: Event) {
    event.preventDefault();
    const foundIdenticalUser = USERS_LIST_MOCK.find(
      (user) => user.email === emailRegisterValue,
    );

    if (foundIdenticalUser) {
      errorMessage1st = "Email already registered.";
      return;
    }

    if (passwordRegister !== passwordRetry) {
      errorMessage2nd = "Passwords do not match.";
      return;
    }

    USERS_LIST_MOCK.push(
      new User(emailRegisterValue.toLowerCase(), passwordRegister),
    );

    registerMessage = "Your account has been created successfully.";

    console.log(USERS_LIST_MOCK);
    setTimeout(() => {
      window.location.href = "/";
    }, 3000);
  }
</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <IconText title="Crea tu cuenta" wrapperClass="header-section" />
    {#if errorMessage1st}
      <!-- Posible componente -->
      <section class="error-message-section">
        <i class="ph ph-warning error-login-message"></i>
        <p class="error-login-message">{errorMessage1st}</p>
      </section>
    {:else if errorMessage2nd}
      <section class="error-message-section">
        <i class="ph ph-warning error-login-message"></i>
        <p class="error-login-message">{errorMessage2nd}</p>
      </section>
    {:else if registerMessage}
      <section class="success-register-section">
        <i class="ph ph-user-check success-message"></i>
        <p class="success-message">{registerMessage}</p>
      </section>
    {/if}

    <!-- FORM -->
    <form class="form-container" onsubmit={handleRegistration}>
      <!-- FORM FIELD -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <Input
            description="Usuario*"
            bind:value={emailRegisterValue}
            input_type={InputTypes.Normal}
            labelProps={{
              for: "register-username",
            }}
            inputProps={{
              type: "email",
              placeholder: "Escribir",
              id: "register-username-id",
              class: "input-primary",
              name: "register-username",
            }}
          />
        </div>
        <div class="form-group">
          <Input
            description="Contraseña*"
            bind:value={passwordRegister}
            input_type={InputTypes.Hidden}
            labelProps={{
              for: "register-password",
            }}
            inputProps={{
              id: "register-password-id",
              class: "input-primary",
              name: "register-password",
            }}
          />
        </div>
        <div class="form-group">
          <Input
            description="Re-ingrese la contraseña*"
            bind:value={passwordRetry}
            input_type={InputTypes.Hidden}
            labelProps={{
              for: "register-password-retry",
            }}
            inputProps={{
              id: "register-password-retry-id",
              class: "input-primary",
              name: "register-password-retry",
            }}
          />
        </div>
      </fieldset>

      <!-- FORM ACTIONS -->
      <section class="form-actions">
        <button class="btn btn-primary btn-login" type="submit"
          >Registrarse</button
        >
        <div class="register-section">
          <p>¿Ya tenes una cuenta?</p>
          <a href="./" class="register-link">Inicia sesion</a>
        </div>
      </section>
    </form>
  </main>
</section>

<style>
  /* Esto es solo para que lo vean no se vuelvan locos */
  .error-message-section {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: var(--error-text-color);
    border-radius: 1em;
    padding: 0.5em;
    width: 70%;
  }
  .error-login-message,
  success-message {
    color: white;
  }

  .success-register-section {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: greenyellow;
    border-radius: 1em;
    padding: 0.5em;
    width: 70%;
  }
</style>
