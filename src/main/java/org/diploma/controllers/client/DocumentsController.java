package org.diploma.controllers.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.diploma.controllers.client.model.DocumentCreateForm;
import org.diploma.controllers.client.model.DocumentSaveForm;
import org.diploma.dao.DocumentDao;
import org.diploma.model.Document;
import org.diploma.model.User;
import org.diploma.utils.CommonUtils;
import org.diploma.utils.GitUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("FieldCanBeLocal")
@Controller
@RequestMapping("/documents")
public class DocumentsController {
    @Autowired private DocumentDao documentDao;
    final private ObjectMapper objectMapper = new ObjectMapper();

    private String APPLICATION_PATH = "c:\\CollaborativeWriting";

    private String USER_TEST_LOGIN = "2222";

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

//    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
//    public ModelAndView editDocument(@PathVariable int id) throws Exception {
//        final Optional<Document> documentOptional = documentDao.getDocumentById(id);
//        if (documentOptional.isPresent()) {
//            final ModelAndView modelAndView = new ModelAndView("/client/document");
//
//            modelAndView.addObject("document", documentOptional.get());
//
//            return modelAndView;
//        }
//
//        throw new Exception("Resource not found");
//    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView editDocument(@PathVariable int id) throws Exception {
        final Optional<Document> documentOptional = documentDao.getDocumentById(id);

        if (documentOptional.isPresent()) {
            final Document document = documentOptional.get();
            final String repositoryPath = repositoryPath(USER_TEST_LOGIN, document.getTitle());

            final String content = GitUtils.getLastContent(repositoryPath);
            document.setContent(content);
            System.out.println(content);

            final ModelAndView modelAndView = new ModelAndView("/client/document");

            modelAndView.addObject("document", document);

            return modelAndView;
        }

        throw new Exception("Resource not found");
    }

    @RequestMapping(value = "/{id}/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveDocument(DocumentSaveForm form) {
//        documentDao.saveDocument(form);

        try {
//            final String repositoryPath = repositoryPath("login", form.getTitle());
            final String repositoryPath = repositoryPath(USER_TEST_LOGIN, "Test document 1");
            final File file = new File(repositoryPath + File.separator + "/content.md");

            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file.toPath())) {
                bufferedWriter.write(form.getContent());
            }

            final User user = new User();
            user.setLogin("Alexander Semenets");
            user.setEmail("satoshisadie@gmai.com");

            GitUtils.commitDocumentChanges(repositoryPath, "Message", user);
        } catch (IOException | GitAPIException e) {
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

        try {
            final String repositoryPath = repositoryPath(USER_TEST_LOGIN, form.getTitle());

            final User user = new User();
            user.setLogin("Alexander Semenets");
            user.setEmail("satoshisadie@gmai.com");

            GitUtils.createInitialStructure(repositoryPath, user);
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }

        return "redirect:./" + documentId + "/edit";
    }

    private String repositoryPath(String userLogin, String documentTitle) {
        return APPLICATION_PATH + File.separator + userLogin + File.separator + documentTitle;
    }
}
