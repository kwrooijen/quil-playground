
## Vector Math


### add (sub)
Add x y of two vectors. Used to move vector to a new location (velocity).

Sub x y of two vectors. This is the opposite of add, but the usage of this is to
calculate the distance between two vectors (velocity).

### mult
Change the length of a vector (scale)
- vector * (vector 0.5 0.5) half length.
- vector * (vector 5 5) length times 5.

### magnitude
pythagoras theorem. The length of the vector

### normalize
reduce a vector to a single unit. This is useful to create any length vector.

For example: `(-> some-vec v/normalize (v/mult 30))`
Changes the length of the vector to 30, but preserves the angle.
