package enedino.andre.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

@Path("/pdf")
public class PDFResource {

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response gerarPDF() {
        // Criar o documento PDF
        Document document = new Document(PageSize.A4);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Criar o escritor de PDF vinculado ao OutputStream
            PdfWriter.getInstance(document, baos);

            // Abrir o documento
            document.open();

            // Adicionar conteúdo ao documento
            document.add(new Paragraph("Este é um exemplo de PDF gerado pelo Quarkus!"));

            // Fechar o documento
            document.close();

            // Retornar o PDF como resposta
            return Response.ok(baos.toByteArray())
                    .header("Content-Disposition", "attachment; filename=\"exemplo.pdf\"")
                    .build();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao gerar o PDF: " + e.getMessage())
                    .build();
        }
    }


    @GET
    @Path("/cabecalho")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response gerarPDFCabecalho() {
        // Criar o documento PDF
        Document document = new Document(PageSize.A4);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Criar o escritor de PDF vinculado ao OutputStream
            PdfWriter.getInstance(document, baos);

            // Abrir o documento
            document.open();

            // Definir a fonte Times
            Font timesFont = new Font(Font.TIMES_ROMAN, 12);
            Font boldFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);

            // Adicionar o cabeçalho centralizado com fonte Times
            Paragraph header = new Paragraph();
            header.add(new Paragraph("SECRETARIA DE ESTADO DA SEGURANÇA PUBLICA", timesFont));
            header.add(new Paragraph("POLICIA CIVIL DO PARANÁ", boldFont));
            header.add(new Paragraph("DELEGACIA DE POLICIA DE PINHAIS", timesFont));
            header.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(header);

            // Adicionar mais conteúdo ao documento, se necessário
            document.add(new Paragraph("\nEste é um exemplo de PDF gerado pelo Quarkus com um cabeçalho centralizado e fonte Times. " +
                    "dhgfghdsghdsfghghhgdfghdfghfdghdfsghdfsghjdfsgjhdfsghjdfsjhgdfsjhgdfshgjdfshgj hgdfhgjfdhgjdfhgjdfjhgdfhgjdfsjhgdfhgjdfshgjdfshjgdfs" +
                    "djkdskjdsakjdsakjdsakjdsakjadskjladskjadskjdsakjldsakjladskjladskjladskjladskjladskjladskjls"));

            rodape(document);


            // Fechar o documento
            document.close();

            // Retornar o PDF como resposta
            return Response.ok(baos.toByteArray())
                    .header("Content-Disposition", "attachment; filename=\"exemplo.pdf\"")
                    .build();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao gerar o PDF: " + e.getMessage())
                    .build();
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    private void rodape(Document document) throws IOException, DocumentException, WriterException {
        // Adicionar a imagem ao rodapé
        InputStream imageStream = getClass().getResourceAsStream("/img/rodape.png");
        if (imageStream != null) {
            Image image = Image.getInstance(imageStream.readAllBytes());

            // Definir o novo tamanho da imagem
            float desiredWidth = 250f; // Largura desejada em pontos
            float desiredHeight = 70f; // Altura desejada em pontos

            // Escalar a imagem para o tamanho desejado
            image.scaleToFit(desiredWidth, desiredHeight);

            // Verificar as dimensões da página e da imagem
            float pageWidth = PageSize.A4.getWidth();
            float pageHeight = PageSize.A4.getHeight();
            float imageWidth = image.getScaledWidth();
            float imageHeight = image.getScaledHeight();

            // Calcular a posição X para centralizar a imagem horizontalmente
            float x = (pageWidth - imageWidth) / 2;

            // Calcular a posição Y para posicionar a imagem no rodapé
            float y = document.bottomMargin() - 15;

            // Definir a posição absoluta da imagem
            image.setAbsolutePosition(x, y);
            document.add(image);
            qrCode(document, x, y);
        } else {
            throw new IOException("Imagem não encontrada.");
        }
    }

    private void qrCode(Document document, float x, float y) throws IOException, DocumentException, WriterException {

        // URL que será codificada no QR Code
        String youtubeUrl = "https://www.youtube.com";
        // Criar o writer de QR Code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // Gerar a matriz de bits para o QR Code
        BitMatrix bitMatrix = qrCodeWriter.encode(youtubeUrl, BarcodeFormat.QR_CODE, 100, 100);
        // Converter a matriz de bits para uma imagem BufferedImage
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        // Salvar a imagem do QR Code em um arquivo
        File qrCodeFile = new File("qrcode.png");
        ImageIO.write(qrImage, "PNG", qrCodeFile);
        System.out.println("QR Code gerado e salvo como qrCode.png");

        // Adicionar a imagem do QR Code ao lado direito do rodapé
        InputStream qrCodeImageStream = getClass().getResourceAsStream("/img/qrcode.png");
        Image qrCodeImage = Image.getInstance(Files.readAllBytes(qrCodeFile.toPath()));

        // Definir o novo tamanho da imagem do QR Code
        float qrCodeWidth = 60f; // Largura desejada em pontos
        float qrCodeHeight = 40f; // Altura desejada em pontos

        // Escalar a imagem do QR Code para o tamanho desejado
        qrCodeImage.scaleToFit(qrCodeWidth, qrCodeHeight);

        // Definir a posição absoluta da imagem do QR Code
        qrCodeImage.setAbsolutePosition(x + 270, y-5);
        document.add(qrCodeImage);
    }
}
