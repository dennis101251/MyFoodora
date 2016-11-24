package fr.ecp.IS1220.group5.project;

import com.beaut.jcommander.Parameter;

/**
 * Created by dennis101251 on 2016/11/24.
 */
public class Test {

    @Parameter
    private List<String> parameters = new ArrayList<String>();

    @Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity")
    private Integer verbose = 1;

    @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
    private String groups;

    @Parameter(names = "-debug", description = "Debug mode")
    private boolean debug = false;

    JCommanderExample jct = new JCommanderExample();
    String[] argv = { "-log", "2", "-groups", "unit" };
    new JCommander(jct, argv);

    Assert.assertEquals(jct.verbose.intValue(), 2);
}
