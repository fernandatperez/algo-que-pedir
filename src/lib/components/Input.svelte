<script lang="ts">
  import "$lib/css/components-css/input.css";

  import { InputTypes } from "$lib/components/InputPropsI";
  import type { OptionalProps } from "$lib/components/InputPropsI";
  import { toggleVariable } from "$lib/utils";
  
  interface InputPropsI {
    description: string;
    value?: string | number | boolean;
    input_type: InputTypes;
    labelProps?: OptionalProps;
    inputProps?: OptionalProps;
    spanProps?: OptionalProps;
    selectProps?: OptionalProps;
    options?: { value: string; label: string }[];
    checked?: boolean | null | undefined; // no me dejaba sino por el string del value
  }

  let {
    description,
    value = $bindable(),
    input_type = InputTypes.Normal,
    labelProps = {},
    inputProps = {},
    spanProps = {},
    options = [],
    checked = $bindable(),
  }: InputPropsI = $props();

  const eyeSlash = "ph ph-eye-slash";
  const eye = "ph ph-eye";


  type Visibility = true | false;
  let visibility: Visibility = $state(false);

</script>

{#if input_type == InputTypes.Normal}
  <label {...labelProps}>
    <span {...spanProps} data-testid='label-{InputTypes.Normal}'>
      {description}
    </span>

    <input {...inputProps} bind:value={value}
    />
    <!-- Subir -->
    <!-- bind:value={value} -->
  </label>
{:else if input_type == InputTypes.Select}
  <label {...labelProps}>
    <span {...spanProps}> 
      {description} 
    </span>
    <select {...inputProps} bind:value={value}>
      {#each options as option}
        <option value={option.value}> {option.label} </option>
      {/each}
    </select>
  </label>
{:else if input_type == InputTypes.Checkbox}
  <label {...labelProps}>
    <input type="checkbox" {...inputProps} bind:checked={value as boolean} />
    <span {...spanProps}>
      {description}
    </span>
  </label>
{:else}
  <label {...labelProps}>
    <span {...spanProps}  data-testid='label-{InputTypes.Hidden}'>
      {description}
    </span>
    <div class="input-with-icon">
      <button
        class="input-icon"
        aria-label="password-show-btn"
        type="button"
        data-testid='eyeBtn-{labelProps.for}'
        onclick={() => (visibility = toggleVariable(visibility))}
      >
        <i class={visibility ? eye : eyeSlash}></i>
      </button>
      <input
        type={visibility ? "text" : "password"}
        {...inputProps}
        bind:value={value}
         data-testid='input-{InputTypes.Hidden}'
        />
        <!-- Subir -->
        <!-- bind:value={value} -->
    </div>
  </label>
{/if}

<style>
  button {
    top: 1em;
    right: 0.3em;
  }
</style>