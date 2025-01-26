<div align="center">

# JSatori
The java implementation of  [Satori protocol](https://satori.chat/).<br/>
Check out the latest  version on [repsy](https://repsy.io/fhyjs/maven/jsatori/artifacts/org.eu.hanana.reimu.lib.satori/jsatori).
<br/><img src="https://img.shields.io/badge/JDK-21+-brightgreen.svg?style=flat-square" alt="jdk-version"> [![Java CI with Gradle](https://github.com/fhyjs/jsatori/actions/workflows/gradle.yml/badge.svg)](https://github.com/fhyjs/jsatori/actions/workflows/gradle.yml)
</div>

# Catalogue

1. Create a project.
2. Install
3. Configure
4. Extra

## Templates

### Gradle
Add the following repository to your`build.gradle`file
```groovy
implementation 'org.eu.hanana.reimu.lib.satori:jsatori:${VERSION}'
```
### Maven
```xml
<dependency>
  <groupId>org.eu.hanana.reimu.lib.satori</groupId>
  <artifactId>jsatori</artifactId>
  <version>${VERSION}</version>
</dependency>
```

## Example

### Client
Run with args: {address} {token}.
<pre style="font-family:'JetBrains Mono',monospace;font-size:9.8pt;"><span style="color:#cc7832;">public class </span>ClientMain <span style="color:#cc7832;">implements </span>Runnable{<br>    <span style="color:#cc7832;">private static final </span>Logger <span style="color:#9876aa;font-style:italic;">log </span>= LogManager.<span style="font-style:italic;">getLogger</span>(ClientMain.<span style="color:#cc7832;">class</span>)<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    public </span>SatoriClient <span style="color:#9876aa;">client</span><span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    public static </span>ClientMain <span style="color:#9876aa;font-style:italic;">Instance</span><span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    public static </span>String[] <span style="color:#9876aa;font-style:italic;">args</span><span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    public static void </span><span style="color:#ffc66d;">main</span>(String[] args) {<br>        <span style="color:#9876aa;font-style:italic;">log</span>.info(<span style="color:#6a8759;">"Test client starting."</span>)<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">        </span>Thread.<span style="font-style:italic;">currentThread</span>().setUncaughtExceptionHandler(<span style="color:#cc7832;">new </span>Thread.UncaughtExceptionHandler() {<br>            <span style="color:#bbb529;">@Override<br></span><span style="color:#bbb529;">            </span><span style="color:#cc7832;">public void </span><span style="color:#ffc66d;">uncaughtException</span>(Thread t<span style="color:#cc7832;">, </span>Throwable e) {<br>                <span style="color:#9876aa;font-style:italic;">log</span>.fatal(StringUtil.<span style="font-style:italic;">getFullErrorMessage</span>(e))<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">            </span>}<br>        })<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">        </span>ClientMain.<span style="color:#9876aa;font-style:italic;">args</span>=args<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">        </span><span style="color:#9876aa;font-style:italic;">Instance</span>=<span style="color:#cc7832;">new </span>ClientMain()<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">        </span><span style="color:#9876aa;font-style:italic;">Instance</span>.run()<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span>}<br><br>    <span style="color:#bbb529;">@Override<br></span><span style="color:#bbb529;">    </span><span style="color:#cc7832;">public void </span><span style="color:#ffc66d;">run</span>() {<br>        <span style="color:#9876aa;">client</span>=SatoriClient.<span style="font-style:italic;">createSatoriClient</span>(<span style="color:#9876aa;font-style:italic;">args</span>[<span style="color:#6897bb;">0</span>])<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">        </span><span style="color:#9876aa;">client</span>.addAuthenticator(<span style="color:#cc7832;">new </span>AuthenticatorC(<span style="color:#cc7832;">new </span>SignalBodyIdentify(<span style="color:#9876aa;font-style:italic;">args</span>[<span style="color:#6897bb;">1</span>])))<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">        </span><span style="color:#9876aa;">client</span>.open()<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span>}<br>}</pre>

### Server

```java
//Not implemented.
```

# Extra

- [Satori Document](https://satori.js.org/zh-CN/protocol)