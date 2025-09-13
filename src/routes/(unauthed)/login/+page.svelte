<script>
  import FormHeader from "$lib/form-header.svelte";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/pages-css/1-login.css";
  import Input from "$lib/Input.svelte";
  import Password from "$lib/Password.svelte";
</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <FormHeader title="Algo que pedir" />

    <!-- FORM -->
    <form class="form-container" id="form-login">
      <!-- FORM FIELD -->
       <!-- Chequear estos for y type -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <Input label_for="text" label_text="Usuario*" input_type="email" input_placeholder="Usuario" input_id="input-id"/>
          <Password label_for="text" label_text="Contraseña*" password_type="password" password_id="password-id" />
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
