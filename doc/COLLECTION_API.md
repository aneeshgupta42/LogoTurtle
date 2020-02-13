# Team 17 - Collection API Discussion

## 2/13/20
## Cayla Schuval (cas169), Libba Lawrence (ll313), Aneesh Gupta (ag468), Turner Jordan (tgj5)

#### Useability Discussion Notes
 - The Java Collections API is generally easy to use, and presents a beginner friendly way of organizing data in Java programs.
 - Mistakes are not necessarily easy to avoid when using the Collections API, but many of the mistakes we encounter are the fault of the implementors, not the API itself. The API seems to always do what it promises to do (i.e. it doesn't break the contract), but it's not difficult to implement things incorrectly. In all, it can be prone to error, but the errors are usually our fault.
 - There are four collection interfaces that are implemented by concrete classes: List, Set, Deque, and Map. Taking the example of LinkedList, the List and Deque classes are implemented. A Set is a collection that has no duplicate entries. A List is an ordered collection. A Deque is a linear collection that allows entries to be added at both the front and end of the collection. A map is a collection that maps key objects to value objects. Each abstract collection interface provides a method of handling data collections, and are implemented in various combinations to create data structures such as LinkedLists. 
 - For set there are three implementations (HashSet, TreeSet, LinkedHashSet). The number of implementations is representative of whether or not it is a interface because interfaces have more implementations. The fact that it is being used so many times justifies Set being an interface.
 - Concrete collections have one superclass which is collection and the purpose of this is to inherit collections. 
 - Having utility classes helps to make the overall classes more flexible and in general changing an API is bad. The broader you can make it the better. Probably, and it is advisable to use the lower level method rather than the higher.  