# ParcelPickup

Helpful Notes about the DCD:
- The DirectionChooser class achieves high cohesion by simplfying the update method in MyAutoController.
Also I think it might help with low Coupling becauseof indirection but Im not sure about this(because TileScores 
linked to the Direction Chooser is just one class but still it represents a lot of different tile types). Even if it is not Indirection
Any future change in the behaviour of the desired system(for example we dont want to pick up parcels anymore or we introduced a new parcel
 with higher priority) will be easier to implement. Finally if we calculate the scores at getBestDirection() method, it will be
computationally costly everytime we ask for the best direction. So it would be better if we implement that in the constructor.
I dont know if we need to write this or this is even a thing but it is similar to the reasoning behind the getFaceValue method of the
dice showed in class.
- TileScores class achieves polymorphism because instead of branching for every type of tile everywhere we just assign them scores
and do our algorithm according to that(I think I have an algorithm in mind I hope it works:))So it would be much easier to implement
any changes in the tiles.
- Everytime we choose a direction, we create a new instance of this. This is not optimum in performance. But it is necessary not to
increase coupling since if we didnt want to create it every time we use it, we would need to store it in MyAutoController which
increases coupling.(Is this Pure Fabrication or smth?)