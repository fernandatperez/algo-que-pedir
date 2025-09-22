<script lang="ts">
  import type { OptionalProps } from "$lib/types"

  interface ImagePropsI {
    imageURL: string
    imageDescription: string
    imageProps?: OptionalProps
  }

  let {
    imageURL = "$lib/assets/img/defaultImage.png",
    imageDescription = "Your image description",
    imageProps = {}
  }: ImagePropsI = $props()

  // Por aca no falta un $bindable()? Por que funciona igual...
</script>

<img
  src={imageURL || "/src/lib/assets/img/defaultImage.png"}
  alt={imageDescription}
  {...imageProps}
>