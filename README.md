# Test task

For a given csv-file find the one second-window during which the largest number of trades took place. Do the same considering trades for each exchange separately.

The input file is passed as a program argument.

Output based on given file:

```
Total max: 516 at 10:02:00.735 - 10:02:01.735
Max for exchange P: 55 at 10:14:23.576 - 10:14:24.576
Max for exchange Q: 243 at 10:02:00.184 - 10:02:01.184
Max for exchange B: 11 at 10:13:00.435 - 10:13:01.435
Max for exchange D: 47 at 10:02:00.785 - 10:02:01.785
Max for exchange V: 9 at 10:12:01.057 - 10:12:02.057
Max for exchange X: 9 at 10:02:00.780 - 10:02:01.780
Max for exchange Y: 12 at 10:09:54.386 - 10:09:55.386
Max for exchange J: 8 at 10:09:54.386 - 10:09:55.386
Max for exchange Z: 43 at 10:02:00.632 - 10:02:01.632
Max for exchange K: 133 at 10:02:00.632 - 10:02:01.632
```

#### Main concept

Add every trade in a queue while the difference between the head element and the new element is less than 1 second. Otherwise, get the current queue size and pop the elements until the difference becomes less than 1 second again.

The same logic is applied to all queues (the general one and per exchange)
