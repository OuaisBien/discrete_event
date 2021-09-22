# Discrete Event
## Introduction
<ul>
<li>A discrete event simulator is a software that simulates the changes in the state of a 
system across time, with each transition from one state of the system to another
triggered via an event. Such a system can be used to study many complex real-world
systems such as queuing to order food at a fast-food restaurant.</li>
<li>An event occurs at a particular time, and each event alters the state of the system and
may generate more events. States remain unchanged between two events (hence the
term discrete), and this allows the simulator to jump from the time of one event to
another. Some simulation statistics are also typically tracked to measure the
performance of the system. </li>
<li>
  The following illustrates a queuing system comprising a single service point S with one
customer queue.<img width="1000" alt="illustration" src="https://user-images.githubusercontent.com/77609596/134296101-3ba594f3-a8ce-4444-a147-b2f4ebe05ee3.png">
</li>
<li>
In this project, we will model a multi-server system comprising the following:</br></br>
<ol>
   <li>There are a number of servers; each server can serve one customer at a time. </li></br></br>
   <li>Each customer has a service time (time taken to serve the customer). </li></br></br>
   <li>When a customer arrives (ARRIVE event):</br></br>
   <ol>
    <li> If the server is idle (not serving any customer), then the server starts serving the customer immediately (SERVE event).</li></br></br>
    <li>  If the server is serving another customer, then the customer that just arrived waits in the queue (WAIT event).</li></br></br>
    <li> If the server is serving one customer with a second customer waiting in the queue, and a third customer arrives, then this latter customer leaves (LEAVE event). In other words, there is at most one customer waiting in the queue. </li></ol></li></br></br>
  <li> When the server is done serving a customer (DONE event), the server can start serving the customer waiting at the front of the queue (if any).</li>
  <li>. If there is no waiting customer, then the server becomes idle again.</li></br></br>
</ol>
</li>
<li> Notice from the above description that there are five events in the system,
namely: ARRIVE, SERVE, WAIT, LEAVE and DONE. For each customer, these are the only
possible event transitions:
  <ul>
  <li> ARRIVE → SERVE → DONE </li>
  <li> ARRIVE → WAIT → SERVE → DONE </li>
  <li> ARRIVE → LEAVE </li>
  </ul>
  </li>
<li>In essence, an event is tied to one customer. Depending on the current state of the system, triggering an event will result in the next state of the system, and possibly the next event. Events are also processed via a queue. At the start of the simulation, the queue only contains the customer arrival events. With every simulation time step, an event is retrieved from the queue to be processed, and any resulting event added to the queue. This process is repeated until there are no events left in the queue.</li>
</ul>
</br></br>

## java.util

### Priority Queuing
<ul>
<li>The PriorityQueue (a mutable class) can be used to keep a collection of elements, where
each element is given a certain priority.
Elements may be added to the queue with the add(E e) method. The queue is modified;
The poll() method may be used to retrieve and remove the element with the highest
priority from the queue. It returns an object of type E, or null if the queue is empty.
The queue is modified.
To enable PriorityQueue to order events, instantiate a PriorityQueue object using the
constructor that takes in a Comparator object. For more details, refer to the Java API
  <a href="https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html"> Specifications</a>.</li>
</ul>
</br></br>

## Implementation
<ul>
  <li>For different event status classes in this project, the are initialised with a `customer` and a timestamp
  </li>
</ul>
