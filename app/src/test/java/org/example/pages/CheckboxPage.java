package org.example.pages;

import java.util.ArrayList;
import java.util.Arrays;

import org.example.util.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckboxPage {
  private WebDriver browser;
  private String expandButtonXpath = "//span[text()=\"%s\"]/../../button";
  private String checkboxXpath = "//span[text()=\"%s\"]/../span[1]";

  @FindBy(id = "result")
  private WebElement result;

  CheckboxPage(WebDriver browser) {
    this.browser = browser;
    PageFactory.initElements(browser, this);
  }

  public static CheckboxPage init(WebDriver browser) {
    return new CheckboxPage(browser);
  }

  public void expandPath(String path) throws InterruptedException {
    var nodes = path.split("\\.");
    var parent = browser.findElement(By.tagName("body"));

    for (String node : nodes) {
      var button = expand(parent, node);
      parent = button.findElement(By.xpath("../../ol"));
      Thread.sleep(500);
    }
  }

  private WebElement expand(WebElement element, String name) {
    var expandButton = element.findElement(By.xpath(String.format(expandButtonXpath, name)));
    expandButton.click();
    return expandButton;
  }

  public void checkNode(String path) throws InterruptedException {
    var nodes = Arrays.asList(path.split("\\."));
    var parentNodes = nodes.subList(0, nodes.size() - 2);
    var lastNode = nodes.get(nodes.size() - 1);

    var parent = browser.findElement(By.tagName("body"));

    for (String node : parentNodes) {
      var checkbox = parent.findElement(By.xpath(String.format(checkboxXpath, node)));
      parent = checkbox.findElement(By.xpath("../../../ol"));
      Thread.sleep(500);
    }

    parent.findElement(By.xpath(String.format(checkboxXpath, lastNode))).click();
  }

  public void validateSelectedItems(String[] expectedSelectedItems) {
    var nodes = result.findElements(By.xpath("./span[position() > 1]"));
    var selectedItems = new ArrayList<>();

    for (WebElement node : nodes) {
      selectedItems.add(node.getText());
    }

    for (int i = 0; i < nodes.size(); i++) {
      if (!expectedSelectedItems[i].equals(selectedItems.get(i))) {
        throw new RuntimeException("Expected selected itmes does not match");
      }
    }
  }
}
