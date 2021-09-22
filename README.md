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
  <li> When the server is done serving a customer (DONE event), the server can start serving the customer waiting at the front of the queue (if any).</li></br></br>
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
  <li>The <code>PriorityQueue</code> (a mutable class) can be used to keep a collection of elements, where
each element is given a certain priority.
Elements may be added to the queue with the <code>add(E e)</code> method. The queue is modified;
The <code>poll()</code> method may be used to retrieve and remove the element with the highest
priority from the queue. It returns an object of type <code>E</code>, or null if the queue is empty.
The queue is modified.
    To enable <code>PriorityQueue</code> to order events, instantiate a <code>PriorityQueue</code> object using the
    constructor that takes in a <code>Comparator</code> object. For more details, refer to the Java API
  <a href="https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html"> Specifications</a>.</li>
</ul>
</br></br>

## Implementation
### Specifying Everything Yourself
<ul>
  <li>For different event status classes in this project, the are mostly initialised with a <code>Customer</code> and a timestamp.
  </li>
  <li>Time will be represented by a three-decimal-place double starting from 0.000. It will also follow usual arithmetics.
  </li>
  <li>For every level, a tally of customers served and average of customer's waiting length will be calculated and dislplayed after the simulation.
  </li>
  <li> In this project, we have several levels of implementation, ranging from naïve to shomewhat complicated, as follows:
  </br>
    <ul>
      <li>Level 1: Given the number of servers and a set of customer arrival times in chronological order,
output the discrete events. Also output the statistics at the end of the simulation.
The driver class reads in a series of arrival times, creates the arrival events, and
starts the simulation. Note that you should ensure your text input is correct</li></br></br>
      <li>Level 2: Rather than a constant service time, each customer now has its own service time. In
addition, each server now has a queue of customers to allow multiple customers to queue
up. A customer that chooses to join a queue joins at the tail. When a server is done
serving a customer, it serves the next waiting customer at the head of the queue.
Hence, the queue should be a first-in-first-out (FIFO) structure. The driver class
reads the number of servers, the maximum queue length, as well as arrival and service
times of each customer.</li></br></br>
      <li>Level 3 (<code>GreedyCustomer</code>): When a customer arrives, he or she first scans through the servers (in order,
from 1 to k) to see if there is an idle server (i.e. not serving a customer). If there
is one, the customer will go to the server to be served. Otherwise, a typical customer
just chooses the first queue (while scanning from servers 1 to k) that is still not
full to join. However, other than the typical customer, a greedy customer is introduced
that always joins the queue with the fewest customers. In the case of a tie, the
customer breaks the tie by choosing the first one while scanning from servers 1 to k.
If a customer cannot find any queue to join, he/she will leave the shop. The driver
class reads the number of servers and the maximum queue length. This is followed
by N lines, each representing the arrival time and service times of the customer, as
  well as whether the customer is greedy or otherwise. We will se <code>Scanner</code>'s <code>nextBoolean()</code> method
to read this boolean value.</li></br></br>
      <li>Level 4 (servers taking rest):The servers (assuming they are all human) are allowed to take occasional breaks. When a
server finishes serving a customer, there is a chance that the server takes a rest for
a certain amount of time. During the break, the server does not serve the next waiting
customer. Upon returning from the break, the server serves the next customer in the
queue (if any) immediately. The driver class first reads the number of servers, the
maximum queue length and the number of customers N. This is followed by N lines, each
representing the arrival time and service times of each customer. The next N lines
depict a chronological sequence of resting times a server takes upon completion of each
service (a value of 0 denotes no rest). Note that we do not pre-determine which rest
time is accorded to which server at the beginning; it can only be decided during the
actual simulation. That is to say, whenever a server rests, it will then know how much
time it has to rest.</li></br></br>
    </ul>
  </ul>
  
### Randomising Everything (Level 5)
<ul>
  <li>Rather than reading arrival, service and resting times from input, we will generate
them as random times instead. A pseudo-randomnumber generator can be initialized with a seed, such that the same seed always
produces the same sequence of (seemingly random) numbers.
<code>RandomGenerator</code> class encapsulates different random
number generators for use in our simulator. Each random number generator generates a
different stream of random numbers.</br>
The constructor for <code>RandomGenerator</code> takes in the following parameters:
<ul>
  <li><code>int</code> seed is the base seed</li>
  <li><code>double</code> lambda is the arrival rate, λ</li>
  <li><code>double</code> mu is the service rate, μ</li>
  <li><code>double</code> rho is the server resting rate, ρ</li>
  </ul>
<li>The inter-arrival time is usually modelled as an exponential random variable,
characterized by a single parameter λ denoting the arrival rate.
The <code>genInterArrivalTime()</code> method of the class <code>RandomGenerator</code> is used for this purpose.
Specifically, start the simulation by generating the first customer arrival event with
timestamp 0 if there are still more customers to simulate, generate the next arrival
event with a timestamp of T + now, where T is generated with te
method <code>genInterArrivalTime()</code>.</li>
<li>The service time is modelled as an exponential random variable, characterized by a
single parameter, service rate μ. The method <code>genServiceTime()</code> from the
class <code>RandomGenerator</code> can be used to generate the service time. Specifically, each time
a customer is being served, a <code>DONE</code> event is generated and scheduled. The <code>DONE</code> event
generated will have a timestamp of T + now, where T is generated with the
method <code>genServiceTime()</code>.</li>
<li>To decide if the server should rest, a random number uniformly drawn from [0, 1] is
generated using the RandomGenerator method <code>genRandomRest()</code>. If the value returned is
less than the probability of resting (Pr) then the server rests. Otherwise, the server
does not rest but continues serving the next customer.</li>
<li>As soon as the server rests, a random rest period Tr is generated using
  the <code>RandomGenerator</code> method <code>genRestPeriod()</code>. This variable is an exponential random
variable, governed by the resting rate, ρ.</li>
<li>In addition, an arriving customer is a <code>GreedyCustomer</code> with probability Pg. To decide
whether a typical or greedy customer is created, a random number uniformly drawn
from [0, 1] is generated with the <code>RandomGenerator</code> method <code>genCustomerType()</code>. If the
value returned is less than Pg, a greedy customer is generated, otherwise, a typical
customer is generated.</br>
  The driver reads the following as input:
  <ul>
  <li>an <code>int</code> representing the number of servers</li>
<li>an <code>int</code> for the maximum queue length, Qmax</li>
<li>an <code>int</code> representing the number of customers to simulate</li>
<li>an <code>int</code> denoting the base seed for the <code>RandomGenerator</code> object</li>
<li>a positive <code>double</code> parameter for the arrival rate, λ</li>
<li>a positive <code>double</code> parameter for the service rate, μ</li>
<li>a positive <code>double</code> parameter for the resting rate, ρ</li>
<li>a <code>double</code> parameter for the probability of resting, Pr</li>
<li>a <code>double</code> parameter for the probability of a greedy customer occurring, Pg</li>
  </ul>
  </li>
</ul>
</br></br>

## Remark
<ul>
  <li>The MainX.java are driver classes for each corresponding level. The folder ~/project/simulator will contain most .class files.</li>
  <li>Most things in this projects are <code>private</code> and <code>final</code> because this is a course project and there were certain rules to be strictly followed.</li>
  <li>To use the test case and see its output, you need to compile all the files. Then in your terminal change to your file's directory. Say if you want to see output for Level1 using leveltest2.in as input, simply type </br>
    <code>java Main1&ltlevel1test2.in</code> and hit enter.</li> 
  </ul>
