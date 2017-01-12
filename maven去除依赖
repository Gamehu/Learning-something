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
    ![去除jar](https://github.com/Gamehu/files/blob/master/Image%203.png)
