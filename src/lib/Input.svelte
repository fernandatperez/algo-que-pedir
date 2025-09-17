<script lang="ts">
  import "$lib/css/components-css/input.css";
  
  enum InputTypes {
    Normal = 'normal',
    Hidden = 'hidden'
  } // No lo toma, por algun motivo

  interface InputPropsI {
    description: string,
    value: string, // -> No termino de entender que es, donde esta, y por que lo pasaria como prop?
    input_type: string,
    labelProps?: Record<string, unknown>, // -> any and unknown are the same in terms of what is assignable to them
    inputProps?: Record<string, unknown>,
    spanProps?: Record<string, unknown>
    // HTMLInputElements: HTMLInputElement // -> Como reparte las propiedades?
  }

  let {
    description,
    value,
    input_type = InputTypes.Normal,
    labelProps = {},
    inputProps = {},
    spanProps = {},
  }: InputPropsI = $props()

</script>

<label {...labelProps}>
  <span {...spanProps}>
    {description}
  </span>
  <input {...inputProps} />
</label>
