<script lang="ts">
  import type { OptionalProps } from "$lib/components/InputPropsI"

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

<style>
  .img-product-edit {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 1.5em;
}
</style>

<img
  src={imageURL || "/src/lib/assets/img/defaultImage.png"}
  alt={imageDescription}
  {...imageProps}
>