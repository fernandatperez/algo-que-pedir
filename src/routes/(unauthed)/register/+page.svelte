<script>
  import FormHeader from "$lib/form-header.svelte";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/2-register.css";
</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <FormHeader title="Crea tu cuenta" />

    <!-- FORM -->
    <form class="form-container">
      <!-- FORM FIELD -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <label for="login-user" class="label-color">Usuario*</label>
          <input
            type="email"
            placeholder="Escribir"
            id="login-user"
            class="input-primary"
          />
        </div>
        <div class="form-group">
          <label for="login-password" class="label-color">Contraseña*</label>
          <div class="input-with-icon">
            <input
              type="password"
              id="login-password"
              class="input-primary input-password"
            />
            <i class="ph ph-eye-slash input-icon"></i>
          </div>
        </div>

        <!-- FORM VALIDATION (Por ahora no lo validamos)  -->
        <div class="form-group">
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
        </div>
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
