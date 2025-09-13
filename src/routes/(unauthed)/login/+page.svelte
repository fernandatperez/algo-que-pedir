<script>
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/pages-css/1-login.css";
  import Input from "$lib/Input.svelte";
</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <header class="header-section">
      <div class="pot-container">
        <i class="ph-fill ph-cooking-pot"></i>
      </div>
      <span class="title-header">Algo que pedir</span>
    </header>

    <!-- FORM -->
    <form class="form-container" id="form-login">
      <!-- FORM FIELD -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <Input label_for="text" label_text="Usuario*" input_type="email" input_placeholder="Usuario" input_id="input-id"/>
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
      </fieldset>

      <!-- FORM ACTIONS -->
      <section class="form-actions">
        <button class="btn btn-primary btn-login"
          ><a href="/orders">Iniciar Sesión</a></button
        >
        <div class="register-section">
          <p>¿No tenes una cuenta?</p>
          <a href="/register" class="register-link">Registrate</a>
        </div>
      </section>
    </form>
  </main>
</section>
