# Math Solver

数学求解器，直接输入算术表达式即得结果

支持以下函数

```java 
new Serial("exp", EXP),
			new Serial("ln", LN),
			new Serial("re", RE),
			new Serial("im", IM),
			new Serial("sqrt", SQRT),
			new Serial("diff",DIFF),
			new Serial("abs", ABS),
			new Serial("norm", NORM),
			new Serial("arg", ARG),
			new Serial("sin", SIN),
			new Serial("cos", COS),
			new Serial("co", CO),
			new Serial("tan", TAN),
			new Serial("arcsin", ARCSIN),
			new Serial("arccos", ARCCOS),
			new Serial("arctan", ARCTAN),
			new Serial("gamma", GAMMA),
			new Serial("Γ", GAMMA), // greek alphabet version !
			new Serial("floor", FLOOR),
			new Serial("max", MAX),
			new Serial("min", MIN),
			new Serial("ceil", CEIL),
			new Serial("reg", REG),
			new Serial("conj", CONJ),
			new Serial("rand", RAND),
			new Serial("round", ROUND),
			new Serial("diff", DIFF, 1),
			new Serial("limit", LIMIT, 1),
			new Serial("eval", EVAL, 1),
			new Serial("fzero", FZERO, 1),
			new Serial("integ", INTEG, 1),
			new Serial("sum", SUM, 1),
			new Serial("perm", PERM),
			new Serial("comb", COMB),
			new Serial("prec", PREC),
			new Serial("base", BASE)
```



## 示例

### input

```
fzero(x-9,0)
```

### output

```
9
```

![image-20240318185245227](C:\Users\colds\AppData\Roaming\Typora\typora-user-images\image-20240318185245227.png)



## 附加功能

### 函数图像绘制

```
y = sin(x)+1
```

![image-20240318185113481](C:\Users\colds\AppData\Roaming\Typora\typora-user-images\image-20240318185113481.png)



# a math solver 


## fucntions

- solve equation

input
```
fzero(x*x-1,0)
```
The f function includs two parameters,the first is the equation ,for example `x^2-1` means the equation `x^2 -1 = 0`,the second parameter is the approximate value which is uesd to reduce the calculation,

output
```
1
```

- common math-physics variable

things like `pi` `e`


- common math function

thinggs like`sin` `cos` `sqrt` ,

you can directy use them in the expression

- plot the graph

give the function it can draw the graph precisely

example:
```
y = sin(x)+1
```

