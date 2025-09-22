<script lang="ts">
  import IconText from "$lib/IconText.svelte";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/2-register.css";
  
  import Input from "$lib/Input.svelte"
  import { InputTypes } from "$lib/types";

</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <IconText title="Crea tu cuenta" wrapperClass="header-section" />

    <!-- FORM -->
    <form class="form-container">
      <!-- FORM FIELD -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <Input
            description="Usuario*"
            value=""
            input_type={InputTypes.Normal}
            labelProps={{
              for: "register-username"
            }}
            inputProps={{
              type: "email",
              placeholder: "Escribir",
              id: "register-username-id",
              class: "input-primary"
            }}
          />
        </div>
        <div class="form-group">
          <Input
            description="Contraseña*"
            value=""
            input_type={InputTypes.Hidden}
            labelProps={{
              for: "register-password"
            }}
            inputProps={{
              id: "register-password-id",
              class: "input-primary",
              name: "register-password"
            }}
          />
        </div>
        <div class="form-group">
          <Input
            description="Re-ingrese la contraseña*"
            value=""
            input_type={InputTypes.Hidden}
            labelProps={{
              for: "register-password-retry"
            }}
            inputProps={{
              id: "register-password-retry-id",
              class: "input-primary",
              name: "register-password-retry"
            }}
          />
        </div>
        <!-- FORM VALIDATION (Por ahora no lo validamos)  -->
        <!-- <div class="form-group">
          <label for="form-validation" class="label-color"
            >Re-ingrese la contraseña*</label
          >
          <div class="input-with-icon">
            <input
              type="password"
              id="form-validation"
              class="input-primary input-password invalid-input"
            />
            <i class="ph ph-eye-slash input-icon"></i>
          </div>
          <div class="invalid-container">
            <i class="ph ph-warning-circle"></i>
            <div class="invalid-text">Las contraseñas no coinciden</div>
          </div>
        </div> -->
      </fieldset>

      <!-- FORM ACTIONS -->
      <section class="form-actions">
        <button class="btn btn-primary btn-login">Registrarse</button>
        <div class="register-section">
          <p>¿Ya tenes una cuenta?</p>
          <a href="./" class="register-link">Inicia sesion</a>
        </div>
      </section>
    </form>
  </main>
</section>
