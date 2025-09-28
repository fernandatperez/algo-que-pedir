<script lang="ts">
  import "$lib/css/components-css/input.css";

  import { InputTypes } from "$lib/InputTypes";
  import type { OptionalProps } from "$lib/InputTypes";
  import { toggleVariable } from "$lib/utils";

  interface InputPropsI {
    description: string;
    input_type: InputTypes;
    labelProps?: OptionalProps;
    inputProps?: OptionalProps;
    spanProps?: OptionalProps;
  }

  let {
    description,
    input_type = InputTypes.Normal,
    labelProps = {},
    inputProps = {},
    spanProps = {},
  }: InputPropsI = $props();

  const eyeSlash = "ph ph-eye-slash";
  const eye = "ph ph-eye";


  type Visibility = true | false;
  let visibility: Visibility = $state(false);
</script>

{#if input_type == InputTypes.Normal}
  <label {...labelProps}>
    <span {...spanProps}>
      {description}
    </span>

    <input {...inputProps}/>
  </label>
{:else}
  <label {...labelProps}>
    <span {...spanProps}>
      {description}
    </span>
    <div class="input-with-icon">
      <button
        class="input-icon"
        aria-label="password-show-btn"
        type="button"

        onclick={() => (visibility = toggleVariable(visibility))}
      >
        <i class={visibility ? eye : eyeSlash}></i>
      </button>
      <input
        type={visibility ? "text" : "password"}
        {...inputProps}
      />
    </div>
  </label>
{/if}

<style>
  button {
    top: 1em;
    right: 0.3em;
  }
</style>
