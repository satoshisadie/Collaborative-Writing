package org.diploma.controllers.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.diploma.controllers.client.model.DocumentCreateForm;
import org.diploma.controllers.client.model.DocumentSaveForm;
import org.diploma.dao.DocumentDao;
import org.diploma.model.Document;
import org.diploma.utils.CommonUtils;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/documents")
public class DocumentsController {
    @Autowired private DocumentDao documentDao;
    final private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView viewDocuments() {
        final ModelAndView modelAndView = new ModelAndView("/client/documents");

        final List<Document> documents = documentDao.getDocuments();
        modelAndView.addObject("documents", documents);

        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDocument(@PathVariable int id) {
        final ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView editDocument(@PathVariable int id) throws Exception {
        final Optional<Document> documentOptional = documentDao.getDocumentById(id);
        if (documentOptional.isPresent()) {
            final ModelAndView modelAndView = new ModelAndView("/client/document");

            modelAndView.addObject("document", documentOptional.get());

            return modelAndView;
        }

        throw new Exception("Resource not found");
    }

    @RequestMapping(value = "/{id}/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveDocument(DocumentSaveForm form) {
//        documentDao.saveDocument(form);

        final Path path = Paths.get("c:/CollaborativeWriting/" + form.getDocumentId());

        final ArrayList<String> strings = new ArrayList<>();
        strings.add(form.getContent());

        try {
            Files.write(path, strings);
//            gitCommit();
            getAllCommits();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return CommonUtils.prepareSuccessResponse().toString();
    }

    @RequestMapping(value = "/{id}/json", method = RequestMethod.GET)
    @ResponseBody
    public String getDocumentJson(@PathVariable int id) throws JsonProcessingException {
        final Optional<Document> documentOptional = documentDao.getDocumentById(id);
        if (documentOptional.isPresent()) {
            final JsonNode documentJsonNode = objectMapper.valueToTree(id);

            final ObjectNode responseObjectNode = CommonUtils.prepareSuccessResponse();

            responseObjectNode
                    .with("data")
                    .set("document", documentJsonNode);

            return responseObjectNode.toString();
        }

        return CommonUtils.prepareErrorResponse("Resource not found").toString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newDocument() {
        return "/client/new-document";
    }

    @RequestMapping(value = "/submit-new", method = RequestMethod.POST)
    public String submitNewDocument(DocumentCreateForm form) {
        final int documentId = documentDao.createDocument(form);

        return "redirect:./" + documentId + "/edit";
    }

    public void gitCommit() throws IOException, GitAPIException {
//        final ProcessBuilder processBuilder = new ProcessBuilder(
//                "cmd.exe",
//                "/c",
//                "cd \"c:\\CollaborativeWriting\" && dir && git add * && git commit -m \"Message\""
////                "cd \"c:\\CollaborativeWriting\" ; git add *; git commit -m \"Message\""
////                "cd \"c:\\CollaborativeWriting\" ; git add *"
////                "git add * && git commit -m \"Message\""
//        );
//        processBuilder.redirectErrorStream(true);

//        final Process process = processBuilder.start();
//
//        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//        String line;
//        while ((line = bufferedReader.readLine()) != null) {
//            System.out.println(line);
//        }

        final Git git = Git.open(new File("c:\\CollaborativeWriting"));

        final CreateBranchCommand createBranchCommand = git.branchCreate();
//        createBranchCommand.

        final AddCommand addCommand = git.add();
        addCommand.addFilepattern(".").call();

        final CommitCommand commitCommand = git.commit();
        commitCommand.setMessage("Commit with lib");

        final RevCommit revCommit = commitCommand.call();
        final String name = revCommit.getName();

        System.out.println(name);
    }

    private void getAllCommits() throws IOException {
        final Git git = Git.open(new File("c:\\CollaborativeWriting"));

//        final DiffCommand diff = git.diff();
//        diff.

        final Repository repository = git.getRepository();

        final RevWalk revWalk = new RevWalk(repository);
        revWalk.setTreeFilter(TreeFilter.ANY_DIFF);
        revWalk.markStart(revWalk.parseCommit(repository.resolve(Constants.HEAD)));

        for (RevCommit commit : revWalk) {
            final String fullMessage = commit.getFullMessage();
            System.out.println("Message:");
            System.out.println(fullMessage);

            System.out.println("Commit:");
            System.out.println(commit.getName());
        }
    }
}
