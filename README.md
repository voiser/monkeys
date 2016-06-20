# monkeys

An exercise on java synchronization.

# The problem

A bunch of monkeys use a rope to cross a canyon. Any number of monkeys can use the rope 
as long as they cross in the same direction. It takes one second to enter the rope and
four seconds to cross the canyon. Two monkeys can't enter at the same time. 

# The implemented solution

Use a waiting queue and dispatch each monkey in order of arrival when the rope is available.

When a new monkey arrives:

  - If the rope is empty, the monkey enters directly to it.
  - In other case:
    - If there are monkeys waiting in the other side of the rope, wait until the last one of them 
      has left the rope.
    - In other case, wait until the last monkey in the rope has entered.

# Running tests

  $ ./gradlew clean test
  
# Running the application

  $ ./gradlew clean run


