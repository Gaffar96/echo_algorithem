# echo_algorithem
Echo Algorithm:
First ”sleep “all processes of the system. The initiator ” wakes up ”and wakes up all of its neighbors by sending a wake-up message. ¨ If a sleeping node is woken up by a wake-up, it sends concurrently ¨ itself
Wake-up messages to all of its neighbors, with the exception of the node that woke it up itself. If a node that has already woken up receives a wake-up from another neighbor
Message, he does not have to react to it. The wake-up messages spread like this
in the system until all accessible processes have finally woken up.
In order to inform the initiator of the completion of the procedure, echo ¨
Messages sent. Each node also pays how many messages - wakeup or ¨
Echo - he has already received a total of. Is the number of messages received correct
with the number of its neighbors, the process for the node is finished. Is it ¨
If the node is the initiator, he can be sure that all nodes have been visited.
If it is not the initiator, it sends before the algorithm is terminated
another echo message to the node that woke it up before
