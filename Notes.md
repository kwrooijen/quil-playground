
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


## Terms

### Mass
The quantity of matter which a body contains, kgs

### Friction
Constant opposite force

||v|| normal force (magnitude) ???
V^ normalized velocity vector (vector of unit 1)
M ???
-1 * M * ||n|| * V^

TLDR:
velocity normalized, reversed, multiplied by a constant

### Drag force

-1/2 * P * ||v||2 * A

#### P (Rho)
Density

#### ||v|| (Velocity)
Speed, magnitude

#### v^ (Normalized vector)
Direction. Single unit vector.

#### A (Surface Area)

#### C (coefficient)


## Gravitational Attraction

((G * M1 * M2) / d2) * r^

## M (mass)

## d (distance)

## r (direction)

## G universal gravitational constant


# SOHCAHTOA

```
hypotenuse   /|
          /   |
      /       | opposite
   /          |
/             |
--------------|
    Adjacennt
```

Sin = Opposite / Hypotenuse
Cos = Adjacent / Hypotenuse
Tan = Opposite / Adjacent
