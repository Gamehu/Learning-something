		pom.xml通过手动去除依赖的jar包，如果引入的jar包有冲突的
    
    <dependency>
			<groupId>org.apache.axis2</groupId>
			<artifactId>axis2-kernel</artifactId>
			<version>1.5.4</version>
			<exclusions>
				<exclusion>
					<groupId>javax.servlet</groupId>
					<artifactId>servlet-api</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
    或者直接在pom的 dependency  hierarchy视图来去除，如下图：
 ![去除依赖](https://github.com/Gamehu/files/blob/master/maven01.png)
 
 或者直接取出某个jar包的所有依赖，因为有时候我们不知道jar包到底依赖了几个包，所以我们都去除
 	<exclusions><exclusion><groupId>*</groupId><artifactId>*</artifactId></exclusion></exclusions>
