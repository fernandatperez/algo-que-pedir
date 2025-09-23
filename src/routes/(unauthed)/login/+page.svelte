<script lang="ts">
  import IconText from "$lib/IconText.svelte";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/pages-css/1-login.css";
  import Input from "$lib/Input.svelte";
  import { InputTypes } from "$lib/InputTypes";
  import { USERS_LIST_MOCK } from "$lib/Users_Mock";

  let emailLoginValue: string = "";
  let passwordLoginValue: string = "";

  function handleLogin(event: Event) {
    event.preventDefault();
    const foundUser = USERS_LIST_MOCK.find(
      (user) => user.email === emailLoginValue,
    );

    if (foundUser && foundUser.password === passwordLoginValue) {
      window.location.href = "/orders"; // posiblemente haya una mejor manera
    } else {
      errorMessage1st = "Wrong email and password combination.";
      errorMessage2nd = "Have you forgotten your password?";
    }
  }

  let errorMessage1st: string;
  let errorMessage2nd: string;

  // Para Pablo:
  /*
    Como hacer que se vayan los mensajes de error despues de 2 segundos o algo asi
    Como hacer para que no desaparezca el logo y el titulo si las variables no estan definidas al principio (por que pasa?)
    NO ME MATES VOY A PASAR TODO A ARCHIVOS SEPARADOS ESTABA COOKING
    Hay alguna diferencia entre los Records y los HTMLElement o es solo por convencion?
    Mejores maneras de tratar el relocation
    Como hago para que los mensajes no queden guardados en las variables? Sino me tira siempre el mismo error xq la variable tiene un mensaje  
  */
</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <IconText wrapperClass="header-section" />
    {#if errorMessage1st}
      <section class="error-message-section">
        <i class="ph ph-warning error-login-message"></i>
        <p class="error-login-message">{errorMessage1st}</p>
        <p class="error-login-message">{errorMessage2nd}</p>
      </section>
    {/if}

    <!-- FORM -->
    <form class="form-container" id="form-login" onsubmit={handleLogin}>
      <!-- FORM FIELD -->
      <!-- Chequear estos for y type -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <!-- Username -->
          <Input
            description="Usuario*"
            bind:value={emailLoginValue}
            input_type={InputTypes.Normal}
            labelProps={{
              for: "login-username",
            }}
            inputProps={{
              class: "input-primary",
              type: "email",
              placeholder: "Usuario",
              id: "login-username-id",
              name: "login-username",
            }}
          />
          <!-- Password -->
          <Input
            description="Contraseña*"
            bind:value={passwordLoginValue}
            input_type={InputTypes.Hidden}
            labelProps={{
              for: "login-password",
            }}
            inputProps={{
              class: "input-primary",
              id: "login-password-id",
              name: "login-password",
            }}
          />
        </div>
      </fieldset>

      <!-- FORM ACTIONS -->
      <section class="form-actions">
        <button type="submit" class="btn btn-primary btn-login">
          Iniciar Sesión
        </button>
        <div class="register-section">
          <p>¿No tenes una cuenta?</p>
          <a href="/register" class="register-link">Registrate</a>
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
  .error-login-message {
    color: white;
  }
</style>
