export function toggleVariable(variable: boolean) {
  if (!variable) {
    variable = true
  } else {
    variable = false
  }
  return variable
}