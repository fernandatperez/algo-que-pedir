<script lang="ts">
  import "$lib/css/components-css/input.css";
  import { InputTypes } from "$lib/types";
  import type { OptionalProps } from "$lib/types"
  import { toggleVariable } from "$lib/toggleFunction";

  interface InputPropsI {
    description: string;
    value: string; // -> No termino de entender que es, donde esta, y por que lo pasaria como prop?
    input_type: InputTypes; // -> Por que no anda si lo defino aca mismo y si lo importo si?
    labelProps?: OptionalProps; // -> any and unknown are the same in terms of what is assignable to them. Unknown contains each and every type in TypeScript
    inputProps?: OptionalProps;
    spanProps?: OptionalProps;
    //HTMLInputElements: HTMLInputElement // -> Como reparte las propiedades?
  }

  let {
    description,
    value = $bindable(""),
    input_type = InputTypes.Normal,
    labelProps = {},
    inputProps = {},
    spanProps = {},
  }: InputPropsI = $props();

  // Icon classes
  const eyeSlash = "ph ph-eye-slash";
  const eye = "ph ph-eye";

  type Visibility = true | false
  let visibility: Visibility = $state(false);

</script>

{#if input_type == InputTypes.Normal}
  <label {...labelProps}>
    <span class="label-color" {...spanProps}>
      {description}
    </span>
    <input {...inputProps} bind:value={value}/>
  </label>
{:else}
  <label {...labelProps}>
    <span {...spanProps}>
      {description}
    </span>
    <!-- Medio raro, pero me esta trayendo el icon.css de la pagina -->
    <div class="input-with-icon">
      <button
        class="input-icon"
        aria-label="password-show-btn"
        type="button"
        onclick={() => visibility = toggleVariable(visibility)}
      >
        <i class={visibility ? eye : eyeSlash}></i>
      </button>
      <input type={visibility ? "text" : "password"} {...inputProps} />
    </div>
  </label>
{/if}

<style>
  button {
    top: 1em;
    right: 0.3em;
  }

  label {
    display: flex;
    flex-direction: column;
    gap: 0.2em;
  }
</style>
