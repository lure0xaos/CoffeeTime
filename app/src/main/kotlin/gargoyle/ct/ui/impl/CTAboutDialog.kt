package gargoyle.ct.ui.impl

import gargoyle.ct.ex.CTException
import gargoyle.ct.ui.CTApp
import gargoyle.ct.ui.CTDialog
import gargoyle.ct.ui.CTIconProvider
import gargoyle.ct.ui.util.CTDragHelper
import gargoyle.ct.ui.util.render.GraphPaperLayout
import gargoyle.ct.util.log.Log
import gargoyle.ct.util.messages.impl.CTMessages
import gargoyle.ct.util.util.getResourceBundle
import gargoyle.ct.util.ver.CTVersionInfo
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import javax.swing.*

class CTAboutDialog(app: CTApp, owner: Window) : JDialog(owner.owner, ModalityType.APPLICATION_MODAL),
    CTDialog<Unit> {
    private val about: CTMessages = CTMessages(
        { CTApp::class.getResourceBundle(LOC_ABOUT, it) })

    private val version: CTVersionInfo

    init {
        version = app.versionInfo
        init(app)
    }

    private fun addHyperlink(descriptionKey: String, content: String, address: URI, y: Int) {
        addField(descriptionKey, content, address, y)
    }

    private fun addField(descriptionKey: String, content: String, address: URI?, y: Int) {
        val pane = contentPane
        val descriptionLabel = JLabel(String.format(FORMAT_LABEL, about.getMessage(descriptionKey)))
        adjustFontSize(descriptionLabel, FONT_SMALL)
        descriptionLabel.horizontalAlignment = SwingConstants.RIGHT
        pane.add(descriptionLabel, Rectangle(0, y, 1, 1))
        val contentLabel = address?.let { JHyperlink(content, it) } ?: JLabel(content)
        adjustFontSize(contentLabel, FONT_BIG)
        pane.add(contentLabel, Rectangle(1, y, 1, 1))
        descriptionLabel.labelFor = contentLabel
    }

    private fun adjustFontSize(component: JComponent, mul: Float) {
        val font = component.font
        component.font = font.deriveFont(font.size * mul)
    }

    @Suppress("SameParameterValue")
    private fun addHyperlink(descriptionKey: String, content: String, address: String?, y: Int) {
        try {
            addField(descriptionKey, content, address?.let { URI(it) }, y)
        } catch (e: URISyntaxException) {
            throw CTException(descriptionKey, e)
        }
    }

    private fun addLabel(descriptionKey: String, content: String, y: Int) {
        addField(descriptionKey, content, null, y)
    }

    private fun init(app: CTIconProvider) {
        title = version.projectName
        setIconImage(ImageIcon(app.smallIcon).image)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        val pane = contentPane
        pane.layout = GraphPaperLayout(2, 6, 6, 3)
        addLabel(LABEL_PROJECT_NAME, version.projectName, 0)
        addLabel(LABEL_PROJECT_DESCRIPTION, version.projectDescription, 1)
        addLabel(LABEL_PROJECT_VERSION, version.projectVersion, 2)
        addLabel(LABEL_ORGANIZATION_NAME, version.organizationName, 3)
        addHyperlink(LABEL_ORGANIZATION_URL, version.organizationUrl, version.organizationUrl, 4)
        pane.add(JButton(OkAction(this)), Rectangle(0, 5, 2, 1))
        pack()
    }

    override fun showMe() {
        CTDragHelper.setLocationRelativeTo(this, owner)
        isVisible = true
    }

    private class JHyperlink(message: String, val address: URI) :
        JLabel("<html><u>$message</u></html>") {
        init {
            foreground = Color.BLUE.darker()
            cursor = Cursor(Cursor.HAND_CURSOR)
            addMouseListener(HyperlinkLabelMouseAdapter())
        }

        private inner class HyperlinkLabelMouseAdapter : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                try {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(address)
                    }
                } catch (ex: UnsupportedOperationException) {
                    Log.error(ex, ex.localizedMessage)
                } catch (ex: IOException) {
                    Log.error(ex, ex.localizedMessage)
                }
            }
        }
    }

    private class OkAction(private val dialog: CTAboutDialog) : AbstractAction("OK") {
        override fun actionPerformed(e: ActionEvent) {
            dialog.dispose()
        }

        public override fun clone(): OkAction {
            return super.clone() as OkAction
        }

    }

    companion object {
        private const val FONT_BIG = 1.2f
        private const val FONT_SMALL = 0.8f
        private const val FORMAT_LABEL = "%s:"
        private const val LABEL_ORGANIZATION_NAME = "label_organization_name"
        private const val LABEL_ORGANIZATION_URL = "label_organization_url"
        private const val LABEL_PROJECT_DESCRIPTION = "label_project_description"
        private const val LABEL_PROJECT_NAME = "label_project_name"
        private const val LABEL_PROJECT_VERSION = "label_project_version"
        private const val LOC_ABOUT = "about"
    }
}
