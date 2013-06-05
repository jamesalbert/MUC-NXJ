#
# dijkstra : implements the disjkstra algorithm to find the shortest
#            path from one node to any other on a network.
#
#            (c) copyright 1998 manifestation.com. all rights reserved.
#            this program is free software, and may be copied or modified
#            under the terms of the GNU general public license, at:
#
#            http://www.fsf.org/copyleft/gpl.html

=head1 .
-----------------------------------------
Given my initial data set, this program will print:


a                      0     a
b                     50     a -> b
c                     60     a -> b -> c
d                     10     a -> d
e                    125     a -> b -> e


What that means is that, for example, the
shortest distance from a to e is 125 units,
and to do it, you go from a to b, and from b
to e. Simple, eh?
-----------------------------------------
=cut

######################### variables #######

my $infinity = "inf";

my @node = ( "a", "b", "c", "d", "e" );
my $root = "a";

my @u = @node;
my @s;

my %dist;
my %edge;
my %prev;

################# the map ####

# all this stuff would be read from a database
# an "edge" is a road or set of directions between two
# points on the map. The number signifies the length of
# the line.

$edge{a}{b} = 50;
$edge{a}{d} = 10;

$edge{b}{c} = 10;
$edge{b}{e} = 75;

$edge{c}{b} = 10;
$edge{c}{d} = 77;

$edge{d}{c} = 77;
$edge{d}{a} = 10;

############################ tools ############

# a sorting option that accounts for infinity
sub bydistance {
   $dist{$a} eq $infinity ? +1 :
   $dist{$b} eq $infinity ? -1 :
       $dist{$a} <=> $dist{$b};
}

############################ the algorithm ####

# first, set all distances to infinity
foreach $n (@node) { $dist{$n} = $infinity; $prev{$n}=$n; }

# .. except the source
$dist{$root} = 0;

# loop while we have unsolved nodes
while (@u) {

    # sort unsolved by distance from root
    @u = sort bydistance @u;

    # we'll solve the closest node.
    push @s, $n = shift @u;

    # now, look at all the nodes connected to n
    foreach $n2 (keys %{$edge{$n}}) {

	# .. and find out if any of their estimated distances
	# can be improved if we go through n

	if (($dist{$n2} eq $infinity) ||
	    ($dist{$n2} > ($dist{$n} + $edge{$n}{$n2}) )) {

	    $dist{$n2} = $dist{$n} + $edge{$n}{$n2};
	    $prev{$n2} = $n;
	}

    }

}

##### print the solutions ######

my $path;

format STDOUT =
@<<<<<<<<<<<<<<   @>>>>>     @<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    $n,           $dist{$n}, $path;
.

foreach $n (@node) {
    my $t = $n;
    $path = $t;
    while ($t ne $root) { $t = $prev{$t}; $path = "$t -> " . $path; }
    write;
}

__END__
