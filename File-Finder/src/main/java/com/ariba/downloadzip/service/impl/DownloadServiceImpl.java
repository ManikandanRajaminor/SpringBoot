package com.ariba.downloadzip.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ariba.controller.SearchController;
import com.ariba.downloadzip.service.DownloadService;
import com.ariba.util.FileFinderUtil;

/**
 * @author Shyam
 *
 */
@Service
public class DownloadServiceImpl implements DownloadService {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private FileFinderUtil fileFinderUtil;

	/**
	 * HttpServletResponse response, String requisition_id, String po_id, String
	 * invoice_id
	 */
	@Override
	public void downloadFile(HttpServletResponse response, String line_of_business, String requisition_id, String po_id,
			String invoice_id) throws Exception {
		
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ (requisition_id != null ? requisition_id : po_id != null ? po_id : invoice_id) + "-download.zip");

		String path = fileFinderUtil.getPath(line_of_business, requisition_id, po_id, invoice_id);

		try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {

			Path source = Paths.get(path);

			Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {

					// only copy files, no symbolic links
					if (attributes.isSymbolicLink()) {
						return FileVisitResult.CONTINUE;
					}

					try (FileInputStream fis = new FileInputStream(file.toFile())) {

						Path targetFile = source.relativize(file);
						zos.putNextEntry(new ZipEntry(targetFile.toString()));

						byte[] buffer = new byte[1024];
						int len;
						while ((len = fis.read(buffer)) > 0) {
							zos.write(buffer, 0, len);
						}

						zos.closeEntry();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) {
					logger.error("Error: ", "Unable to zip : %s%n%s%n", file, exc);
					return FileVisitResult.CONTINUE;
				}
			});

		}

	}

	/**
	 * HttpServletResponse response, String requisition_id, String po_id, String
	 * invoice_id
	 */
	@Override
	public void downloadZipFile(HttpServletResponse response, String line_of_business, String requisition_id,
			String po_id, String invoice_id) throws Exception {
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition",
				"attachment; filename=" + requisition_id + "-" + po_id + "-" + invoice_id + "-download.zip");
		String path = null;
		int i;
		try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {

			for (i = 0; i < 3; i++) {
				if (i == 0 && null != requisition_id) {
					path = fileFinderUtil.getPath(line_of_business, requisition_id, null, null);
				} else if (i == 1 && null != po_id) {
					path = fileFinderUtil.getPath(line_of_business, null, po_id, null);
				} else if (i == 2 && null != invoice_id) {
					path = fileFinderUtil.getPath(line_of_business, null, null, invoice_id);
				}

				Path source = Paths.get(path);

				final String id = i == 0 ? requisition_id : i == 1 ? po_id : invoice_id;

				Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {

						// only copy files, no symbolic links
						if (attributes.isSymbolicLink()) {
							return FileVisitResult.CONTINUE;
						}

						try (FileInputStream fis = new FileInputStream(file.toFile())) {

							Path targetFile = source.relativize(file);
							System.out.println(targetFile.toString());
							zos.putNextEntry(new ZipEntry(id+"\\"+targetFile.toString()));

							byte[] buffer = new byte[1024];
							int len;
							while ((len = fis.read(buffer)) > 0) {
								zos.write(buffer, 0, len);
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) {
						logger.error("Error: ", "Unable to zip : %s%n%s%n", file, exc);
						return FileVisitResult.CONTINUE;
					}
				});
			}
			zos.closeEntry();

		}

	}

	/**
	 * @param file
	 * @throws IOException
	 */
	public static void delete(final File file) throws Exception {
		if (file == null)
			return;
		Files.walkFileTree(file.toPath(), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
				// If the root path did not exist, ignore the error; otherwise throw it.
				if (exc instanceof NoSuchFileException && path.toFile().equals(file))
					return FileVisitResult.TERMINATE;
				throw exc;
			}

			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
				Files.delete(path);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {
				Files.delete(path);
				return FileVisitResult.CONTINUE;
			}
		});
	}
}