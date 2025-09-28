<script lang="ts">
  import IconText from "$lib/IconText.svelte";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/pages-css/1-login.css";
  import Input from "$lib/Input.svelte";
  import { InputTypes } from "$lib/InputTypes";
  import { USERS_LIST_MOCK } from "$lib/data/mock/users";
  import { UserType } from "$lib/domain/user";
  import type { ValidationMessage } from "$lib/domain/user";
  import { userService } from "$lib/services/UserService";
  import { showError } from '$lib/domain/errorHandler'
  import ValidationField from "$lib/components/ValidationField.svelte";
  import { goto } from "$app/navigation";
  import { fade } from "svelte/transition";

  let errors: ValidationMessage[] = $state([])

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
      let validation = await userService.getUser(user.username, user.password)
      if (validation) goto ("/orders")
      else {
        errorMessage1st = "Wrong email and password combination"
        errorMessage2nd = "Have you forgotten your password?"
        setTimeout(() => {
          errorMessage1st = ""
          errorMessage2nd = ""
        }, 4000)
      }
      errors = [] // limpiar errores
    } catch (error) {
      showError("Error al crear el ingrediente", error)
    }
  }

  let errorMessage1st: string = $state("")
  let errorMessage2nd: string = $state("")

  console.log(USERS_LIST_MOCK);
</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <IconText wrapperClass="header-section" />
    {#if errorMessage1st}
      <section class="error-message-section" transition:fade>
        <i class="ph ph-warning error-login-message"></i>
        <p class="error-login-message">{errorMessage1st}</p>
        <a href="https://passwords.google.com/" class="error-login-message">{errorMessage2nd}</a>
      </section>
    {/if}
    <!-- FORM -->
    <form class="form-container" id="form-login" onsubmit={onSubmit}>
      <!-- FORM FIELD -->
      <!-- Chequear estos for y type -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <!-- Username -->
          <Input
            description="Usuario*"
            input_type={InputTypes.Normal}
            labelProps={{
              class: "label-color",
              for: "username",
            }}
            inputProps={{
              class: "input-primary",
              type: "email",
              placeholder: "Usuario",
              id: "input-id",
              name: "username",
            }}
          />
          <ValidationField errors={errors} field="username" />
          <!-- Password -->
          <Input
            description="Contraseña*"
            input_type={InputTypes.Hidden}
            labelProps={{
              class: "label-color",
              for: "password",
            }}
            inputProps={{
              class: "input-primary",
              id: "password-id",
              name: "password",
            }}
          />
          <ValidationField errors={errors} field="password" />
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

  a {
    text-decoration: underline;
  }
</style>
