
.
inputPlaceholder*
shape: *
dtype0
4
PlaceholderPlaceholder*
shape: *
dtype0
H
random_normal/shapeConst*
dtype0*
valueB"      
?
random_normal/meanConst*
dtype0*
valueB
 *    
A
random_normal/stddevConst*
dtype0*
valueB
 *  ?
~
"random_normal/RandomStandardNormalRandomStandardNormalrandom_normal/shape*
dtype0*
seed2 *

seed *
T0
[
random_normal/mulMul"random_normal/RandomStandardNormalrandom_normal/stddev*
T0
D
random_normalAddrandom_normal/mulrandom_normal/mean*
T0
[
weight1
VariableV2*
	container *
shape
:*
dtype0*
shared_name 
~
weight1/AssignAssignweight1random_normal*
use_locking(*
T0*
validate_shape(*
_class
loc:@weight1
F
weight1/readIdentityweight1*
T0*
_class
loc:@weight1
C
random_normal_1/shapeConst*
dtype0*
valueB:
A
random_normal_1/meanConst*
dtype0*
valueB
 *    
C
random_normal_1/stddevConst*
dtype0*
valueB
 *  ?

$random_normal_1/RandomStandardNormalRandomStandardNormalrandom_normal_1/shape*
dtype0*
seed2 *

seed *
T0
a
random_normal_1/mulMul$random_normal_1/RandomStandardNormalrandom_normal_1/stddev*
T0
J
random_normal_1Addrandom_normal_1/mulrandom_normal_1/mean*
T0
U
bias1
VariableV2*
	container *
shape:*
dtype0*
shared_name 
z
bias1/AssignAssignbias1random_normal_1*
use_locking(*
T0*
validate_shape(*
_class

loc:@bias1
@

bias1/readIdentitybias1*
T0*
_class

loc:@bias1
T
MatMulMatMulinputweight1/read*
transpose_a( *
T0*
transpose_b( 
'
addAddMatMul
bias1/read*
T0
 
SigmoidSigmoidadd*
T0
J
random_normal_2/shapeConst*
dtype0*
valueB"      
A
random_normal_2/meanConst*
dtype0*
valueB
 *    
C
random_normal_2/stddevConst*
dtype0*
valueB
 *  ?

$random_normal_2/RandomStandardNormalRandomStandardNormalrandom_normal_2/shape*
dtype0*
seed2 *

seed *
T0
a
random_normal_2/mulMul$random_normal_2/RandomStandardNormalrandom_normal_2/stddev*
T0
J
random_normal_2Addrandom_normal_2/mulrandom_normal_2/mean*
T0
[
weight2
VariableV2*
	container *
shape
:*
dtype0*
shared_name 

weight2/AssignAssignweight2random_normal_2*
use_locking(*
T0*
validate_shape(*
_class
loc:@weight2
F
weight2/readIdentityweight2*
T0*
_class
loc:@weight2
C
random_normal_3/shapeConst*
dtype0*
valueB:
A
random_normal_3/meanConst*
dtype0*
valueB
 *    
C
random_normal_3/stddevConst*
dtype0*
valueB
 *  ?

$random_normal_3/RandomStandardNormalRandomStandardNormalrandom_normal_3/shape*
dtype0*
seed2 *

seed *
T0
a
random_normal_3/mulMul$random_normal_3/RandomStandardNormalrandom_normal_3/stddev*
T0
J
random_normal_3Addrandom_normal_3/mulrandom_normal_3/mean*
T0
U
bias2
VariableV2*
	container *
shape:*
dtype0*
shared_name 
z
bias2/AssignAssignbias2random_normal_3*
use_locking(*
T0*
validate_shape(*
_class

loc:@bias2
@

bias2/readIdentitybias2*
T0*
_class

loc:@bias2
Z

matmul_100MatMulSigmoidweight2/read*
transpose_a( *
T0*
transpose_b( 
-
add_1Add
matmul_100
bias2/read*
T0
$
	Sigmoid_1Sigmoidadd_1*
T0

LogLog	Sigmoid_1*
T0
%
mulMulPlaceholderLog*
T0
2
sub/xConst*
dtype0*
valueB
 *  ?
'
subSubsub/xPlaceholder*
T0
4
sub_1/xConst*
dtype0*
valueB
 *  ?
)
sub_1Subsub_1/x	Sigmoid_1*
T0

Log_1Logsub_1*
T0
!
mul_1MulsubLog_1*
T0
!
add_2Addmulmul_1*
T0

RankRankadd_2*
T0
5
range/startConst*
dtype0*
value	B : 
5
range/deltaConst*
dtype0*
value	B :
:
rangeRangerange/startRankrange/delta*

Tidx0
@
costMeanadd_2range*
	keep_dims( *
T0*

Tidx0

NegNegcost*
T0
6
gradients/ShapeShapeNeg*
T0*
out_type0
<
gradients/ConstConst*
dtype0*
valueB
 *  ?
A
gradients/FillFillgradients/Shapegradients/Const*
T0
6
gradients/Neg_grad/NegNeggradients/Fill*
T0
B
gradients/cost_grad/ShapeShapeadd_2*
T0*
out_type0
T
gradients/cost_grad/SizeSizegradients/cost_grad/Shape*
T0*
out_type0
H
gradients/cost_grad/addAddrangegradients/cost_grad/Size*
T0
_
gradients/cost_grad/modFloorModgradients/cost_grad/addgradients/cost_grad/Size*
T0
V
gradients/cost_grad/Shape_1Shapegradients/cost_grad/mod*
T0*
out_type0
I
gradients/cost_grad/range/startConst*
dtype0*
value	B : 
I
gradients/cost_grad/range/deltaConst*
dtype0*
value	B :

gradients/cost_grad/rangeRangegradients/cost_grad/range/startgradients/cost_grad/Sizegradients/cost_grad/range/delta*

Tidx0
H
gradients/cost_grad/Fill/valueConst*
dtype0*
value	B :
f
gradients/cost_grad/FillFillgradients/cost_grad/Shape_1gradients/cost_grad/Fill/value*
T0
­
!gradients/cost_grad/DynamicStitchDynamicStitchgradients/cost_grad/rangegradients/cost_grad/modgradients/cost_grad/Shapegradients/cost_grad/Fill*
N*
T0
G
gradients/cost_grad/Maximum/yConst*
dtype0*
value	B :
q
gradients/cost_grad/MaximumMaximum!gradients/cost_grad/DynamicStitchgradients/cost_grad/Maximum/y*
T0
i
gradients/cost_grad/floordivFloorDivgradients/cost_grad/Shapegradients/cost_grad/Maximum*
T0
x
gradients/cost_grad/ReshapeReshapegradients/Neg_grad/Neg!gradients/cost_grad/DynamicStitch*
T0*
Tshape0
v
gradients/cost_grad/TileTilegradients/cost_grad/Reshapegradients/cost_grad/floordiv*

Tmultiples0*
T0
D
gradients/cost_grad/Shape_2Shapeadd_2*
T0*
out_type0
C
gradients/cost_grad/Shape_3Shapecost*
T0*
out_type0
G
gradients/cost_grad/ConstConst*
dtype0*
valueB: 
~
gradients/cost_grad/ProdProdgradients/cost_grad/Shape_2gradients/cost_grad/Const*
	keep_dims( *
T0*

Tidx0
I
gradients/cost_grad/Const_1Const*
dtype0*
valueB: 

gradients/cost_grad/Prod_1Prodgradients/cost_grad/Shape_3gradients/cost_grad/Const_1*
	keep_dims( *
T0*

Tidx0
I
gradients/cost_grad/Maximum_1/yConst*
dtype0*
value	B :
n
gradients/cost_grad/Maximum_1Maximumgradients/cost_grad/Prod_1gradients/cost_grad/Maximum_1/y*
T0
l
gradients/cost_grad/floordiv_1FloorDivgradients/cost_grad/Prodgradients/cost_grad/Maximum_1*
T0
X
gradients/cost_grad/CastCastgradients/cost_grad/floordiv_1*

SrcT0*

DstT0
c
gradients/cost_grad/truedivRealDivgradients/cost_grad/Tilegradients/cost_grad/Cast*
T0
A
gradients/add_2_grad/ShapeShapemul*
T0*
out_type0
E
gradients/add_2_grad/Shape_1Shapemul_1*
T0*
out_type0

*gradients/add_2_grad/BroadcastGradientArgsBroadcastGradientArgsgradients/add_2_grad/Shapegradients/add_2_grad/Shape_1*
T0

gradients/add_2_grad/SumSumgradients/cost_grad/truediv*gradients/add_2_grad/BroadcastGradientArgs*
	keep_dims( *
T0*

Tidx0
t
gradients/add_2_grad/ReshapeReshapegradients/add_2_grad/Sumgradients/add_2_grad/Shape*
T0*
Tshape0

gradients/add_2_grad/Sum_1Sumgradients/cost_grad/truediv,gradients/add_2_grad/BroadcastGradientArgs:1*
	keep_dims( *
T0*

Tidx0
z
gradients/add_2_grad/Reshape_1Reshapegradients/add_2_grad/Sum_1gradients/add_2_grad/Shape_1*
T0*
Tshape0
m
%gradients/add_2_grad/tuple/group_depsNoOp^gradients/add_2_grad/Reshape^gradients/add_2_grad/Reshape_1
Ή
-gradients/add_2_grad/tuple/control_dependencyIdentitygradients/add_2_grad/Reshape&^gradients/add_2_grad/tuple/group_deps*
T0*/
_class%
#!loc:@gradients/add_2_grad/Reshape
Ώ
/gradients/add_2_grad/tuple/control_dependency_1Identitygradients/add_2_grad/Reshape_1&^gradients/add_2_grad/tuple/group_deps*
T0*1
_class'
%#loc:@gradients/add_2_grad/Reshape_1
G
gradients/mul_grad/ShapeShapePlaceholder*
T0*
out_type0
A
gradients/mul_grad/Shape_1ShapeLog*
T0*
out_type0

(gradients/mul_grad/BroadcastGradientArgsBroadcastGradientArgsgradients/mul_grad/Shapegradients/mul_grad/Shape_1*
T0
Z
gradients/mul_grad/mulMul-gradients/add_2_grad/tuple/control_dependencyLog*
T0

gradients/mul_grad/SumSumgradients/mul_grad/mul(gradients/mul_grad/BroadcastGradientArgs*
	keep_dims( *
T0*

Tidx0
n
gradients/mul_grad/ReshapeReshapegradients/mul_grad/Sumgradients/mul_grad/Shape*
T0*
Tshape0
d
gradients/mul_grad/mul_1MulPlaceholder-gradients/add_2_grad/tuple/control_dependency*
T0

gradients/mul_grad/Sum_1Sumgradients/mul_grad/mul_1*gradients/mul_grad/BroadcastGradientArgs:1*
	keep_dims( *
T0*

Tidx0
t
gradients/mul_grad/Reshape_1Reshapegradients/mul_grad/Sum_1gradients/mul_grad/Shape_1*
T0*
Tshape0
g
#gradients/mul_grad/tuple/group_depsNoOp^gradients/mul_grad/Reshape^gradients/mul_grad/Reshape_1
±
+gradients/mul_grad/tuple/control_dependencyIdentitygradients/mul_grad/Reshape$^gradients/mul_grad/tuple/group_deps*
T0*-
_class#
!loc:@gradients/mul_grad/Reshape
·
-gradients/mul_grad/tuple/control_dependency_1Identitygradients/mul_grad/Reshape_1$^gradients/mul_grad/tuple/group_deps*
T0*/
_class%
#!loc:@gradients/mul_grad/Reshape_1
A
gradients/mul_1_grad/ShapeShapesub*
T0*
out_type0
E
gradients/mul_1_grad/Shape_1ShapeLog_1*
T0*
out_type0

*gradients/mul_1_grad/BroadcastGradientArgsBroadcastGradientArgsgradients/mul_1_grad/Shapegradients/mul_1_grad/Shape_1*
T0
`
gradients/mul_1_grad/mulMul/gradients/add_2_grad/tuple/control_dependency_1Log_1*
T0

gradients/mul_1_grad/SumSumgradients/mul_1_grad/mul*gradients/mul_1_grad/BroadcastGradientArgs*
	keep_dims( *
T0*

Tidx0
t
gradients/mul_1_grad/ReshapeReshapegradients/mul_1_grad/Sumgradients/mul_1_grad/Shape*
T0*
Tshape0
`
gradients/mul_1_grad/mul_1Mulsub/gradients/add_2_grad/tuple/control_dependency_1*
T0

gradients/mul_1_grad/Sum_1Sumgradients/mul_1_grad/mul_1,gradients/mul_1_grad/BroadcastGradientArgs:1*
	keep_dims( *
T0*

Tidx0
z
gradients/mul_1_grad/Reshape_1Reshapegradients/mul_1_grad/Sum_1gradients/mul_1_grad/Shape_1*
T0*
Tshape0
m
%gradients/mul_1_grad/tuple/group_depsNoOp^gradients/mul_1_grad/Reshape^gradients/mul_1_grad/Reshape_1
Ή
-gradients/mul_1_grad/tuple/control_dependencyIdentitygradients/mul_1_grad/Reshape&^gradients/mul_1_grad/tuple/group_deps*
T0*/
_class%
#!loc:@gradients/mul_1_grad/Reshape
Ώ
/gradients/mul_1_grad/tuple/control_dependency_1Identitygradients/mul_1_grad/Reshape_1&^gradients/mul_1_grad/tuple/group_deps*
T0*1
_class'
%#loc:@gradients/mul_1_grad/Reshape_1
o
gradients/Log_grad/Reciprocal
Reciprocal	Sigmoid_1.^gradients/mul_grad/tuple/control_dependency_1*
T0
t
gradients/Log_grad/mulMul-gradients/mul_grad/tuple/control_dependency_1gradients/Log_grad/Reciprocal*
T0
o
gradients/Log_1_grad/Reciprocal
Reciprocalsub_10^gradients/mul_1_grad/tuple/control_dependency_1*
T0
z
gradients/Log_1_grad/mulMul/gradients/mul_1_grad/tuple/control_dependency_1gradients/Log_1_grad/Reciprocal*
T0
C
gradients/sub_1_grad/ShapeConst*
dtype0*
valueB 
I
gradients/sub_1_grad/Shape_1Shape	Sigmoid_1*
T0*
out_type0

*gradients/sub_1_grad/BroadcastGradientArgsBroadcastGradientArgsgradients/sub_1_grad/Shapegradients/sub_1_grad/Shape_1*
T0

gradients/sub_1_grad/SumSumgradients/Log_1_grad/mul*gradients/sub_1_grad/BroadcastGradientArgs*
	keep_dims( *
T0*

Tidx0
t
gradients/sub_1_grad/ReshapeReshapegradients/sub_1_grad/Sumgradients/sub_1_grad/Shape*
T0*
Tshape0

gradients/sub_1_grad/Sum_1Sumgradients/Log_1_grad/mul,gradients/sub_1_grad/BroadcastGradientArgs:1*
	keep_dims( *
T0*

Tidx0
D
gradients/sub_1_grad/NegNeggradients/sub_1_grad/Sum_1*
T0
x
gradients/sub_1_grad/Reshape_1Reshapegradients/sub_1_grad/Neggradients/sub_1_grad/Shape_1*
T0*
Tshape0
m
%gradients/sub_1_grad/tuple/group_depsNoOp^gradients/sub_1_grad/Reshape^gradients/sub_1_grad/Reshape_1
Ή
-gradients/sub_1_grad/tuple/control_dependencyIdentitygradients/sub_1_grad/Reshape&^gradients/sub_1_grad/tuple/group_deps*
T0*/
_class%
#!loc:@gradients/sub_1_grad/Reshape
Ώ
/gradients/sub_1_grad/tuple/control_dependency_1Identitygradients/sub_1_grad/Reshape_1&^gradients/sub_1_grad/tuple/group_deps*
T0*1
_class'
%#loc:@gradients/sub_1_grad/Reshape_1

gradients/AddNAddNgradients/Log_grad/mul/gradients/sub_1_grad/tuple/control_dependency_1*
N*
T0*)
_class
loc:@gradients/Log_grad/mul
W
$gradients/Sigmoid_1_grad/SigmoidGradSigmoidGrad	Sigmoid_1gradients/AddN*
T0
H
gradients/add_1_grad/ShapeShape
matmul_100*
T0*
out_type0
J
gradients/add_1_grad/Shape_1Const*
dtype0*
valueB:

*gradients/add_1_grad/BroadcastGradientArgsBroadcastGradientArgsgradients/add_1_grad/Shapegradients/add_1_grad/Shape_1*
T0

gradients/add_1_grad/SumSum$gradients/Sigmoid_1_grad/SigmoidGrad*gradients/add_1_grad/BroadcastGradientArgs*
	keep_dims( *
T0*

Tidx0
t
gradients/add_1_grad/ReshapeReshapegradients/add_1_grad/Sumgradients/add_1_grad/Shape*
T0*
Tshape0

gradients/add_1_grad/Sum_1Sum$gradients/Sigmoid_1_grad/SigmoidGrad,gradients/add_1_grad/BroadcastGradientArgs:1*
	keep_dims( *
T0*

Tidx0
z
gradients/add_1_grad/Reshape_1Reshapegradients/add_1_grad/Sum_1gradients/add_1_grad/Shape_1*
T0*
Tshape0
m
%gradients/add_1_grad/tuple/group_depsNoOp^gradients/add_1_grad/Reshape^gradients/add_1_grad/Reshape_1
Ή
-gradients/add_1_grad/tuple/control_dependencyIdentitygradients/add_1_grad/Reshape&^gradients/add_1_grad/tuple/group_deps*
T0*/
_class%
#!loc:@gradients/add_1_grad/Reshape
Ώ
/gradients/add_1_grad/tuple/control_dependency_1Identitygradients/add_1_grad/Reshape_1&^gradients/add_1_grad/tuple/group_deps*
T0*1
_class'
%#loc:@gradients/add_1_grad/Reshape_1

 gradients/matmul_100_grad/MatMulMatMul-gradients/add_1_grad/tuple/control_dependencyweight2/read*
transpose_a( *
T0*
transpose_b(

"gradients/matmul_100_grad/MatMul_1MatMulSigmoid-gradients/add_1_grad/tuple/control_dependency*
transpose_a(*
T0*
transpose_b( 
z
*gradients/matmul_100_grad/tuple/group_depsNoOp!^gradients/matmul_100_grad/MatMul#^gradients/matmul_100_grad/MatMul_1
Λ
2gradients/matmul_100_grad/tuple/control_dependencyIdentity gradients/matmul_100_grad/MatMul+^gradients/matmul_100_grad/tuple/group_deps*
T0*3
_class)
'%loc:@gradients/matmul_100_grad/MatMul
Ρ
4gradients/matmul_100_grad/tuple/control_dependency_1Identity"gradients/matmul_100_grad/MatMul_1+^gradients/matmul_100_grad/tuple/group_deps*
T0*5
_class+
)'loc:@gradients/matmul_100_grad/MatMul_1
w
"gradients/Sigmoid_grad/SigmoidGradSigmoidGradSigmoid2gradients/matmul_100_grad/tuple/control_dependency*
T0
B
gradients/add_grad/ShapeShapeMatMul*
T0*
out_type0
H
gradients/add_grad/Shape_1Const*
dtype0*
valueB:

(gradients/add_grad/BroadcastGradientArgsBroadcastGradientArgsgradients/add_grad/Shapegradients/add_grad/Shape_1*
T0

gradients/add_grad/SumSum"gradients/Sigmoid_grad/SigmoidGrad(gradients/add_grad/BroadcastGradientArgs*
	keep_dims( *
T0*

Tidx0
n
gradients/add_grad/ReshapeReshapegradients/add_grad/Sumgradients/add_grad/Shape*
T0*
Tshape0

gradients/add_grad/Sum_1Sum"gradients/Sigmoid_grad/SigmoidGrad*gradients/add_grad/BroadcastGradientArgs:1*
	keep_dims( *
T0*

Tidx0
t
gradients/add_grad/Reshape_1Reshapegradients/add_grad/Sum_1gradients/add_grad/Shape_1*
T0*
Tshape0
g
#gradients/add_grad/tuple/group_depsNoOp^gradients/add_grad/Reshape^gradients/add_grad/Reshape_1
±
+gradients/add_grad/tuple/control_dependencyIdentitygradients/add_grad/Reshape$^gradients/add_grad/tuple/group_deps*
T0*-
_class#
!loc:@gradients/add_grad/Reshape
·
-gradients/add_grad/tuple/control_dependency_1Identitygradients/add_grad/Reshape_1$^gradients/add_grad/tuple/group_deps*
T0*/
_class%
#!loc:@gradients/add_grad/Reshape_1

gradients/MatMul_grad/MatMulMatMul+gradients/add_grad/tuple/control_dependencyweight1/read*
transpose_a( *
T0*
transpose_b(

gradients/MatMul_grad/MatMul_1MatMulinput+gradients/add_grad/tuple/control_dependency*
transpose_a(*
T0*
transpose_b( 
n
&gradients/MatMul_grad/tuple/group_depsNoOp^gradients/MatMul_grad/MatMul^gradients/MatMul_grad/MatMul_1
»
.gradients/MatMul_grad/tuple/control_dependencyIdentitygradients/MatMul_grad/MatMul'^gradients/MatMul_grad/tuple/group_deps*
T0*/
_class%
#!loc:@gradients/MatMul_grad/MatMul
Α
0gradients/MatMul_grad/tuple/control_dependency_1Identitygradients/MatMul_grad/MatMul_1'^gradients/MatMul_grad/tuple/group_deps*
T0*1
_class'
%#loc:@gradients/MatMul_grad/MatMul_1
J
GradientDescent/learning_rateConst*
dtype0*
valueB
 *ΝΜΜ=
έ
3GradientDescent/update_weight1/ApplyGradientDescentApplyGradientDescentweight1GradientDescent/learning_rate0gradients/MatMul_grad/tuple/control_dependency_1*
use_locking( *
T0*
_class
loc:@weight1
Τ
1GradientDescent/update_bias1/ApplyGradientDescentApplyGradientDescentbias1GradientDescent/learning_rate-gradients/add_grad/tuple/control_dependency_1*
use_locking( *
T0*
_class

loc:@bias1
α
3GradientDescent/update_weight2/ApplyGradientDescentApplyGradientDescentweight2GradientDescent/learning_rate4gradients/matmul_100_grad/tuple/control_dependency_1*
use_locking( *
T0*
_class
loc:@weight2
Φ
1GradientDescent/update_bias2/ApplyGradientDescentApplyGradientDescentbias2GradientDescent/learning_rate/gradients/add_1_grad/tuple/control_dependency_1*
use_locking( *
T0*
_class

loc:@bias2
λ
GradientDescentNoOp4^GradientDescent/update_weight1/ApplyGradientDescent2^GradientDescent/update_bias1/ApplyGradientDescent4^GradientDescent/update_weight2/ApplyGradientDescent2^GradientDescent/update_bias2/ApplyGradientDescent
6
	Greater/yConst*
dtype0*
valueB
 *   ?
1
GreaterGreater	Sigmoid_1	Greater/y*
T0
-
CastCastGreater*

SrcT0
*

DstT0
*
EqualEqualCastPlaceholder*
T0
-
Cast_1CastEqual*

SrcT0
*

DstT0

Rank_1RankCast_1*
T0
7
range_1/startConst*
dtype0*
value	B : 
7
range_1/deltaConst*
dtype0*
value	B :
B
range_1Rangerange_1/startRank_1range_1/delta*

Tidx0
C
MeanMeanCast_1range_1*
	keep_dims( *
T0*

Tidx0
=
weights2/tagConst*
dtype0*
valueB Bweights2
A
weights2HistogramSummaryweights2/tagweight2/read*
T0
:
cost_1/tagsConst*
dtype0*
valueB Bcost_1
2
cost_1ScalarSummarycost_1/tagsNeg*
T0
=
Merge/MergeSummaryMergeSummaryweights2cost_1*
N
L
initNoOp^weight1/Assign^bias1/Assign^weight2/Assign^bias2/Assign"