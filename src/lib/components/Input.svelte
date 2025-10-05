<script lang="ts">
  import "$lib/css/components-css/input.css"
  import { InputTypes, type InputPropsI } from "$lib/components/InputPropsI";
  import { toggleVariable } from "$lib/utils";

  let {
    label_text,
    label_for,
    input_type = InputTypes.Normal,
    value = $bindable(),
    ...others
  }: InputPropsI = $props()

  const eyeSlash = "ph ph-eye-slash";
  const eye = "ph ph-eye";

  type Visibility = true | false;
  let visibility: Visibility = $state(false);

</script>

{#if input_type == InputTypes.Normal}
<label class="w-100 label-color input-group" for={label_for}>
  <span data-testid='label-normal'>
    {label_text}
  </span>
  <!-- svelte-ignore component_name_lowercase -->
  <input {...others} bind:value={value}>
</label>
{:else}
  <label class="w-100 label-color input-group" for={label_for}>
    <span data-testid='label-hidden'>
      {label_text}
    </span>
    <div class="input-with-icon">
      <button
        class="input-icon"
        aria-label="password-show-btn"
        type="button"
        data-testid='eyeBtn-{label_for}'
        onclick={() => (visibility = toggleVariable(visibility))}
      >
        <i class={visibility ? eye : eyeSlash}></i>
      </button>
      <!-- svelte-ignore component_name_lowercase -->
      <input
        type={visibility ? "text" : "password"}
        {...others}
        bind:value={value}
        data-testid='input-hidden'
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