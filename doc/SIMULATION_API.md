# Team 17 - Simulation API Discussion

## 2/13/20
## Cayla Schuval (cas169), Libba Lawrence (ll313), Aneesh Gupta (ag468), Turner Jordan (tgj5)

-VisualizationView -> External as it helps other people extend our code
-VisualizationModel -> External as it helps run the functionality for our view
- Percolation/Fire/PredatorPrey etc. Cell classes -> Internal they are the simulation rules for behind the scene coding
- SimData and related document classes ->External because they allow the user to format data info
- Neighborhood classes -> Internal because they are the formatting of the neighborhoods

## Designing API
- External -> needs to be able to show the simulation
    - View will select a file
        - Visualization View
    - Pass it to SimData to unpack the data
    - Model will process data and interact with internal code
    - View will present simulation
- Internal -> Add new kinds of simulations
    - Cell is the abstract class
    - Make cell an interface? 
    - Use inheritance to write the simulation with cell
    - Pass the simulation rules to the grid class
    - implement the neighborhood rules and the send the data to the view

- Simulation cell classes are concrete even though cell is abstract because they are used by the grid, and there is no branching off of the simulation classes