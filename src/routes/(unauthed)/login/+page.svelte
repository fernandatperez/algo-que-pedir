<script lang="ts">
  import "$lib/css/components-css/buttons.css"
  import "$lib/css/components-css/icon.css"
  import "$lib/css/pages-css/1-login.css"

  import IconText from "$lib/components/IconText.svelte";

  import { InputTypes } from "$lib/components/InputPropsI";
  import { UserType } from "$lib/domain/user";
  import type { ValidationMessage } from "$lib/domain/user";
  import { userService } from "$lib/services/UserService";
  import { showError } from '$lib/domain/errorHandler'
  import ValidationField from "$lib/components/ValidationField.svelte";
  import { goto } from "$app/navigation";
  import { toasts } from '$lib/components/toast/toastStore'
  import Input from "$lib/components/Input.svelte";

  let errors: ValidationMessage[] = $state([])
  let toastLock: boolean = false

  const onSubmit = async (ev: SubmitEvent) => {
    ev.preventDefault() // cancela el comportamiento por defecto del navegador frente al evento del submit

    // ev.currentTarget: es el elemento que tiene asignado el event listener
    // as HTMLFormElement es un type assertion de TypeScript: le decís explícitamente al compilador “esto es un formulario”
    const form = ev.currentTarget as HTMLFormElement
    const formData = new FormData(form) // creo el formData
    
    // console.info((formData.get("email") ?? "").toString())
    // console.info((formData.get("password") ?? "").toString())

    const user = new UserType(
      "Default name",
      (formData.get("password") ?? "").toString(),
      (formData.get("email") ?? "").toString()
    )

    user.validate()

    if (user.errors.length > 0) {
      errors = [...user.errors]
      return errors
    }

    try {
      let validation = await userService.getUser(user.email, user.password)
      if (validation) goto ("/orders") // si devuelve Truthy redirige a orders, quiza se podria mejorar esto con token? 
      // else {
      //   errorMessages = ["Nombre de usuario y/o contraseña incorrecto/s"]
      //   if(!toastLock) {
      //     errorMessages.forEach((error) => {
      //       toasts.push(error, {type: 'error'})
      //     })
      //   }
      // }
    } catch (error) {
      toasts.push("Nombre de usuario y/o contraseña incorrecto/s", {type: 'error'})
      // showError("Error", error) // manejar el error de manera adecuada
    } finally {
      errors = [] // limpiar errores
    }
  }

  let errorMessages: string[] = $state([])

  // console.log(USERS_LIST_MOCK);
</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <IconText wrapperClass="header-section" />
    <!-- FORM -->
    <form class="form-container" id="form-login" onsubmit={onSubmit}>
      <!-- FORM FIELD -->
      <!-- Chequear estos for y type -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <div class="input-wrapper">
            <Input
              label_text="Usuario*"
              label_for="email"
              input_type={InputTypes.Normal}
              value=""
              class="input-primary"
              type="email"
              placeholder="Usuario"
              id="input-id"
              name="email"
              tabindex={1}
            />
            <ValidationField errors={errors} field="email" />
          </div>

          <div class="input-wrapper">
            <Input
              label_text="Contraseña*"
              label_for="password"
              input_type={InputTypes.Hidden}
              value=""
              class="input-primary"
              id="password-id"
              name="password"
              tabindex={2}
            />
            <ValidationField errors={errors} field="password" />
          </div>
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