package telran.git.test;

import java.util.List;

import telran.git.model.CommitMessage;
import telran.git.model.FileState;
import telran.git.service.GitRepository;
import telran.git.service.GitRepositoryImpl;
import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;


public class GitRepositoryAppl {
public static GitRepository gitRepository;


	public static void main(String[] args) throws Exception {
		Menu menu = new Menu("Git Repository", getItems());
	
		gitRepository = GitRepositoryImpl.init();
	//	gitRepository.addIgnoredFileNameExp("^.\\w+");
		menu.perform(new ConsoleInputOutput());

		gitRepository.save();

	}
	private static Item[] getItems() {
		Item[] res = {
			Item.of("Commit", GitRepositoryAppl::commit),
			Item.of("Create branch", GitRepositoryAppl::createBranch),
			Item.of("Delate branch", GitRepositoryAppl::delateBranch),
			Item.of("Rename branch", GitRepositoryAppl::renameBranch),
			Item.of("Switch branch", GitRepositoryAppl::switchBranch),
			Item.of("Info", GitRepositoryAppl::infoGit),
			Item.of("Log commit", GitRepositoryAppl::logGit),
			Item.exit()
			
		};
		return res;
	} 
	static void commit(InputOutput io) {
		String CommitMessage = io.readString("Input Commit Message ");
		io.writeLine(gitRepository.commit(CommitMessage));	
	}
	static void createBranch(InputOutput io) {
		String branchName = io.readString("Input Branch Name ");
		io.writeLine(gitRepository.createBranch(branchName));
	}
	static void delateBranch(InputOutput io) {
		String branchName = io.readString("Input Branch Name for delate ");
		io.writeLine(gitRepository.deleteBranch(branchName));
	}
	static void renameBranch(InputOutput io) {
		String oldBranchName = io.readString("Input old Branch Name for rename ");
		String newBranchName = io.readString("Input new Branch Name for rename ");
		io.writeLine(gitRepository.renameBranch(oldBranchName, newBranchName));
	}
	static void switchBranch(InputOutput io) {
		String nameBranchOrCommit = io.readString("Input Name branch or commit for switching ");
		io.writeLine(gitRepository.switchTo(nameBranchOrCommit));
	}
	static void infoGit(InputOutput io) {
		List<FileState> listInfo =	gitRepository.info();		
		listInfo.stream().forEach(b->io.writeLine(b));		
	}
	static void logGit(InputOutput io) {
		List<CommitMessage> logtInfo =	gitRepository.log();
		List<String> branchInfo =	gitRepository.branches();
		if (logtInfo.isEmpty()) {
			io.writeLine("No commits");
		} else {
		logtInfo.stream().forEach(b->io.writeLine(b));
		}
		if (branchInfo.isEmpty()) {
			io.writeLine("No branches");
		} else {
		branchInfo.stream().forEach(b->io.writeLine(b));
		}		
	}
}
