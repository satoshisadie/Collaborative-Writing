package org.diploma.controllers.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.diploma.controllers.client.model.DocumentSaveForm;
import org.diploma.dao.DocumentDao;
import org.diploma.model.Document;
import org.diploma.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
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
    public ModelAndView viewDocument(@PathVariable long id) {
        final ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView editDocument(@PathVariable long id) {
        final ModelAndView modelAndView = new ModelAndView("/client/document");

        final Document document = getDocument(id);
        modelAndView.addObject("document", document);

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveDocument(DocumentSaveForm form) {
        documentDao.saveDocument(form);

        return CommonUtils.prepareSuccessResponse().toString();
    }

    @RequestMapping(value = "/{id}/json", method = RequestMethod.GET)
    @ResponseBody
    public String getDocumentJson(@PathVariable long id) throws JsonProcessingException {
        final JsonNode documentJsonNode = objectMapper.valueToTree(getDocument(id));

        final ObjectNode responseObjectNode = CommonUtils.prepareSuccessResponse();

        responseObjectNode
                .with("data")
                .set("document", documentJsonNode);

        return responseObjectNode.toString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newDocument() {
        return "redirect:./" + CommonUtils.generateId() + "/edit";
    }

    private Document getDocument(long id) {
        final Optional<Document> documentOptional = documentDao.getDocumentById(id);

        if (documentOptional.isPresent()) {
            return documentOptional.get();
        }

        final Document document = new Document();

        document.setId(id);
//        document.setAuthorId();
        document.setCreatedDate(new Date());
        document.setContent("");

        return document;
    }
}
