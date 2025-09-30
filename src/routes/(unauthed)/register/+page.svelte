<script lang="ts">
  import IconText from "$lib/components/IconText.svelte";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/2-register.css";

  import Input from "$lib/components/Input.svelte";
  import { InputTypes } from "$lib/components/InputTypes";
  import { USERS_LIST_MOCK } from "$lib/data/mock/users";
  import { UserType, ValidationMessage } from "$lib/domain/user";
  import { userService } from "$lib/services/UserService";
  import { goto } from "$app/navigation";
  import { showError } from "$lib/domain/errorHandler";
  import ValidationField from "$lib/components/ValidationField.svelte";
  import { fade } from "svelte/transition";

  let errors: ValidationMessage[] = $state([])

  let registerMessageNoMatched: string = $state("");
  let successmessage: string = $state("");
  let successmessage2: string = $state("");

  console.log(USERS_LIST_MOCK);

  const onSubmit = async (ev: SubmitEvent) => {
    ev.preventDefault() // cancela el comportamiento por defecto del navegador frente al evento del submit

    // ev.currentTarget: es el elemento que tiene asignado el event listener
    // as HTMLFormElement es un type assertion de TypeScript: le decís explícitamente al compilador “esto es un formulario”
    const form = ev.currentTarget as HTMLFormElement
    const formData = new FormData(form) // creo el formData

    const user = new UserType(
      (formData.get("username") ?? "").toString(),
      (formData.get("password") ?? "").toString()
    )

    user.validate()

    if (user.errors.length > 0) {
      errors = [...user.errors]
      return
    }

    try {
      // Esto no esta devolviendo nada catcheable (->)
      // Ver service y returns
      await userService.alreadyRegisteredUsername(user.username)
      if (formData.get("password") == formData.get("password-retry")) {
        successmessage = "Usuario generado con exito"
        successmessage2 = "Seras redirigido a la pagina de Ingreso"

        await userService.createUser(user)
        setTimeout(() => {
          successmessage = ""
          successmessage2 = ""
          goto ("/")
        }, 3000)
      } else {
        registerMessageNoMatched = "Las contraseñas no coinciden"
        setTimeout(() => {
          registerMessageNoMatched = ""
        }, 3000)
      }
      errors = [] // limpiar errores
    } catch (error) {
      showError("Error al crear el ingrediente", error)
    }
  }
</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <IconText title="Crea tu cuenta" wrapperClass="header-section" />
    {#if registerMessageNoMatched}
      <section class="error-message-section" transition:fade>
        <i class="ph ph-warning error-login-message"></i>
        <p class="error-login-message">{registerMessageNoMatched}</p>
      </section>
    {:else if successmessage}
      <section class="success-register-section" transition:fade>
        <i class="ph ph-user-check success-message"></i>
        <p class="success-message">{successmessage}</p>
        <p class="success-message">{successmessage2}</p>
      </section>
    {/if}

    <!-- FORM -->
    <form class="form-container" onsubmit={onSubmit}>
      <!-- FORM FIELD -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <Input
            description="Usuario*"
            input_type={InputTypes.Normal}
            labelProps={{
              for: "register-username",
            }}
            inputProps={{
              type: "email",
              placeholder: "Escribir",
              id: "register-username-id",
              class: "input-primary",
              name: "username",
            }}
          />
          <ValidationField errors={errors} field="username" />
        </div>
        <div class="form-group">
          <Input
            description="Contraseña*"
            input_type={InputTypes.Hidden}
            labelProps={{
              for: "register-password",
            }}
            inputProps={{
              id: "register-password-id",
              class: "input-primary",
              name: "password",
            }}
          />
          <ValidationField errors={errors} field="password" />
        </div>
        <div class="form-group">
          <Input
            description="Re-ingrese la contraseña*"
            input_type={InputTypes.Hidden}
            labelProps={{
              for: "register-password-retry",
            }}
            inputProps={{
              id: "register-password-retry-id",
              class: "input-primary",
              name: "password-retry",
            }}
          />
          <ValidationField errors={errors} field="password"/>
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
  .form-group {
    gap: 0.5em;
  }
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
