This was tested, and the JAR compiled, using JavaSE 1.8 execution environment.

As you can see on the scrubber, the tick marks are not evenly placed. When playing the animation it should be clear that there is a sense of relative time; a line that took longer to draw will take longer to scrub through than a line of the same length that took less time to draw.

MVC principles were used; there is a single Model class, and JComponent subclasses such as CanvasView.java and ColorView.java acting as Views.

The scrubber works for the purposes of scrubbing along all points of lines (point by point, not stroke by stroke).
Also, if you scrub to some point and start drawing, all strokes past that point are erased as a primitive undo function.

The widgets on the bottom planel are Play Animation from current position, the slider, Jump to Beginning, Jump to End.

File saving is supported; the files are simply txt files with a custom ".dood" (for doodle) extension to more fully filter.
Saving and loading a model will preserve all aspects, including colour, line thickness, and all points.
An example file, test.dood, is included.
