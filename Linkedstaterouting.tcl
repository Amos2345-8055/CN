# Create a simulator instance
set ns [new Simulator]

# Open trace files
set nf [open out.nam w]
$ns namtrace-all $nf

set tr [open out.tr w]
$ns trace-all $tr

# Define finish procedure
proc finish {} {
    global nf ns tr
    $ns flush-trace
    close $tr
    close $nf
    exec nam out.nam &
    exit 0
}

# Create nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

# Create links
$ns duplex-link $n0 $n1 10Mb 10ms DropTail
$ns duplex-link $n1 $n3 10Mb 10ms DropTail
$ns duplex-link $n2 $n1 10Mb 10ms DropTail

# Set link orientations for NAM visualization
$ns duplex-link-op $n0 $n1 orient right-down
$ns duplex-link-op $n1 $n3 orient right
$ns duplex-link-op $n2 $n1 orient right-up

# Create TCP agent and attach to node n0
set tcp [new Agent/TCP]
$ns attach-agent $n0 $tcp

# Create TCPSink agent and attach to node n3
set sink [new Agent/TCPSink]
$ns attach-agent $n3 $sink

# Connect TCP to TCPSink
$ns connect $tcp $sink

# Create FTP application and attach to TCP
set ftp [new Application/FTP]
$ftp attach-agent $tcp

# Create UDP agent and attach to node n2
set udp [new Agent/UDP]
$ns attach-agent $n2 $udp

# Create Null agent and attach to node n3
set null [new Agent/Null]
$ns attach-agent $n3 $null

# Connect UDP to Null
$ns connect $udp $null

# Create CBR application and attach to UDP
set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp

# Simulate link failure and recovery
$ns rtmodel-at 1.0 down $n1 $n3
$ns rtmodel-at 2.0 up $n1 $n3

# Enable routing protocol
$ns rtproto LS

# Schedule events
$ns at 0.0 "$ftp start"
$ns at 0.0 "$cbr start"
$ns at 5.0 "finish"

# Run the simulation
$ns run
