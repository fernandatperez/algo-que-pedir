<script lang="ts">
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/pages-css/1-login.css"
  import "$lib/css/pages-css/2-register.css";
  
  import IconText from "$lib/components/IconText.svelte";
  import { InputTypes } from "$lib/components/InputPropsI";
  import { USERS_LIST_MOCK } from "$lib/data/mock/users";
  import { UserType, ValidationMessage } from "$lib/domain/user";
  import { userService } from "$lib/services/UserService";
  import { goto } from "$app/navigation";
  import { showError } from "$lib/domain/errorHandler";
  import ValidationField from "$lib/components/ValidationField.svelte";
  import { toasts } from '$lib/components/toast/toastStore'
  import Input from "$lib/components/Input.svelte";

  let errors: ValidationMessage[] = $state([])
  let toastLock: boolean = false

  let registerMessageNoMatched: string = $state("");
  let successmessages: string[] = $state([]);

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
      return errors
    }
    try {
      // Esto no esta devolviendo nada catcheable (->)
      // Ver service y returns
      await userService.alreadyRegisteredUsername(user.username)
      if (formData.get("password") == formData.get("password-retry")) {
        successmessages = ['Usuario generado con exito.',' Seras redirigido a la pagina de Ingreso']
        // successmessage2 = "Seras redirigido a la pagina de Ingreso"

        if(!toastLock) {
          successmessages.forEach((message) => {
            toasts.push(message, {type: 'success'})
          })
          toastLock = true
          setTimeout(releaseToast, 5000)
        }

        await userService.createUser(user)
      } else {
        registerMessageNoMatched = "Las contraseñas no coinciden"
        toasts.push(registerMessageNoMatched, {type: 'error'})
        setTimeout(() => {
          registerMessageNoMatched = ""
        }, 3000)
      }
      errors = [] // limpiar errores
    } catch (error) {
      toasts.push("Error al crear el ingrediente", {type: 'error'})
      showError("Error al crear el ingrediente", error)
    }
  }

  const releaseToast = () => {
    toastLock = false
  } 
</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <IconText title="Crea tu cuenta" wrapperClass="header-section" />
    <!-- FORM -->
    <form class="form-container" onsubmit={onSubmit}>
      <!-- FORM FIELD -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <Input
            label_text="Usuario*"
            label_for="username"
            input_type={InputTypes.Normal}
            value=""
            type= "email"
            placeholder= "Escribir"
            id= "register-username-id"
            class= "input-primary"
            name= "username"
          />
          <ValidationField errors={errors} field="username" />
        
          <Input
            label_text="Contraseña*"
            label_for="password"
            input_type={InputTypes.Hidden}
            value=""
            id= "register-password-id"
            class= "input-primary"
            name= "password"
          />
          <ValidationField errors={errors} field="password" />

          <Input
            label_text="Re-ingrese la contraseña*"
            label_for="register-password-retry"
            input_type={InputTypes.Hidden}
            value=""
            id= "register-password-retry-id"
            class= "input-primary"
            name= "password-retry"
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
