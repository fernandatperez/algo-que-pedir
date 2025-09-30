<script lang="ts">
  import type { OptionalProps } from "$lib/components/InputTypes"

  interface ImagePropsI {
    imageURL?: string
    imageDescription: string
    imageProps?: OptionalProps
  }

  let {
    imageURL = "$lib/assets/img/defaultImage.png",
    imageDescription = "Your image description",
    imageProps = {}
  }: ImagePropsI = $props()

</script>

<img
  src={imageURL || "/src/lib/assets/img/defaultImage.png"}
  alt={imageDescription}
  {...imageProps}
>