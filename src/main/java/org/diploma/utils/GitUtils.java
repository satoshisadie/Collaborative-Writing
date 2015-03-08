package org.diploma.utils;

import org.diploma.model.User;
import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class GitUtils {

    private static String INITIAL_CONTENT =
            "Welcome to your new document!\n\n" +
            "Brush up on Markdown: http://daringfireball.net/projects/markdown/basics";

    public static void commitDocumentChanges(String content,
                                             String path,
                                             String commitMessage,
                                             User user) throws IOException, GitAPIException
    {
        final Git git = Git.open(new File(path));

        if (!isBranchExists(git, user.getLogin())) {
            git.branchCreate()
                    .setName(user.getLogin())
                    .call();
        }

        git.checkout()
//                .setName(Constants.MASTER)
                .setName(user.getLogin())
                .call();

        final File file = new File(path + File.separator + "/content.md");

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file.toPath())) {
            bufferedWriter.write(content);
        }

        if (!isAnyDiffs(git)) return;

        final RevCommit revCommit = git.commit()
                .setAuthor(user.getLogin(), user.getEmail())
                .setMessage(commitMessage)
                .setAll(true)
                .call();

//        git.checkout()
//                .setStartPoint(revCommit)
//                .call();

        git.close();
    }

    public static boolean isBranchExists(Git git, String branchName) throws GitAPIException {
        final List<Ref> refList = git.branchList().call();

        return refList.stream()
                .anyMatch(ref -> ref.getName().endsWith(branchName));
    }

    public static boolean isAnyDiffs(Git git) throws IOException, GitAPIException {
        final DiffCommand diffCommand = git.diff();
        final List<DiffEntry> diffEntries = diffCommand.call();

        return !diffEntries.isEmpty();
    }

    public static void createInitialStructure(String path, User user) throws GitAPIException, IOException {
        final InitCommand initCommand = Git.init();
        initCommand.setDirectory(new File(path));
        final Git git = initCommand.call();

        final File contentFile = new File(path + File.separator + "/content.md");

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(contentFile.toPath())) {
            bufferedWriter.write(INITIAL_CONTENT);
        }

        git.add()
                .addFilepattern(".")
                .call();

        git.commit()
                .setAuthor(user.getLogin(), user.getEmail())
                .setMessage("Initial commit")
                .call();

        git.close();
    }

    public static String getLastContent(String path) throws IOException {
        final Git git = Git.open(new File(path));
        final Repository repository = git.getRepository();

        final ObjectId lastCommitId = repository.resolve(Constants.HEAD);

        // A RevWalk allows to walk over commits based on some filtering
        // that is defined
        final RevWalk revWalk = new RevWalk(repository);
        final RevCommit commit = revWalk.parseCommit(lastCommitId);

        // And using commit's tree find the path
        final RevTree tree = commit.getTree();

        // Now try to find a specific file
        final TreeWalk treeWalk = new TreeWalk(repository);

        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create("content.md"));

//        if (!treeWalk.next()) {
//            throw new IllegalStateException("Did not find expected file 'README.md'");
//        }

        final ObjectId objectId = treeWalk.getObjectId(0);
        final ObjectLoader loader = repository.open(objectId);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            loader.copyTo(byteArrayOutputStream);

            revWalk.dispose();
            git.close();

            return byteArrayOutputStream.toString(StandardCharsets.UTF_8.toString());
        }
    }
}
