# Sonar Compliance
Jenkins plugin that help ensure quality compliance policy with help of java script functions of sonar metrics.

Sonar compliance plugin obtains the metrics from primary sonar as well secondary sonar as required.

You provide comma separated JavaScript functions with sonar metric key variables. These functions evaluated to determine the result of this build step. You can find all sonar supported metrics keys at, Metric definitions - SonarQube - Codehaus. Examples include class_complexity, coverage, branch_coverage.

Example:
coverage <= 70

Sonar compliance can be acheived 3 different ploicies.

# Static Complainace Policy
Simplest and fastest of all. Collect metrics from the primary sonar and evaluate the rules and fail/pass the build step. 

# Differential Compliance Policy (Recommended)
You will have 2 sonar runs. You select "Differential" option.

When a user requests a git pull request, you will collect sonar metrics for the targetted base branch where that pull request code eventually gets merged onto. Then you will again collect sonar metrics for the pull request code. You will create a d project for pull request in sonar. Sonar stores them as time series and shows the differences from the previous run. 

All the compliance rules works on these difference of metrics. E.x, if we say "coverage > = 0", the "Coverage for Targetted branch - coverage for pull request >= 0". Meaning, the PR code should be having at least same coverage as tragetted branch.

# Differential Compliance Policy with Secondary Sonar
Here, we always compare the metrics of difference from seconadary sonar to primary sonar. We select "Differential" option and then "Additional Sonar" option to provide secondary sonar values.

So, if we say  "coverage > = 0", then the primary sonar coverage should be as good as the secondary sonar.

If dont want to collect sonar metrics of target branch with every pull request, then you may chose this approach where you periodically (daily) collects secondary metrics. Even though, this is faster, will not help the developer craftsmanship as in the recommended differential compliance policy.


