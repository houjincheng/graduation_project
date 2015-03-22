package anyviewj.interfaces.view;

import com.bluemarsh.jswat.parser.java.analysis.AnalysisAdapter;
import com.bluemarsh.jswat.parser.java.lexer.Lexer;
import com.bluemarsh.jswat.parser.java.lexer.LexerException;
import com.bluemarsh.jswat.parser.java.node.*;
import anyviewj.util.SkipList;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import javax.swing.text.BadLocationException;

/**
 * Class JavaScanner is responsible for lexically scanning a Java source
 * file and generating a list of JavaTokenInfo objects.
 *
 * @author  Nathan Fiedler
 */
public class JavaScanner extends AnalysisAdapter {
    /** Source file reader. */
    private Reader reader;
    /** Source content. */
    private SourceContent content;
    /** List of JavaTokenInfo objects. */
    private SkipList tokenList;

    /**
     * Constructs a JavaScanner to read from the given Reader.
     *
     * @param  r    input reader.
     * @param  doc  source content.
     */
    public JavaScanner(Reader r, SourceContent doc) {
        reader = r;
        content = doc;
        tokenList = new SkipList();
    } // JavaScanner

    /**
     * Scan the input source file and return a SkipList of JavaTokenInfo
     * objects.
     *
     * @return  list of tokens, or null if error.
     * @throws  IOException
     *          if an I/O error occurred.
     * @throws  LexerException
     *          if a lexer error occurred.
     */
    public SkipList scan() throws IOException, LexerException {
        PushbackReader pbr = new PushbackReader(reader);
        Lexer lexer = new Lexer(pbr);
        Token token = lexer.next();
        while (!(token instanceof EOF)) {
            token.apply(this);
            token = lexer.next();
        }
        pbr.close();
        return tokenList;
    } // scan

    /**
     * Determines the offset from the start of the source file of the
     * given token.
     *
     * @param  t  token whose position is in question.
     * @return  zero-based offset of token.
     */
    protected int getOffset(Token t) {
        try {
            int line = t.getLine() - 1;
            int offset = t.getPos() - 1;
            offset += content.getLineStartOffset(line);
            return offset;
        } catch (BadLocationException ble) {
            return -1;
        }
    } // getOffset

    /**
     * Handle a keyword token.
     *
     * @param  node  token representing a keyword.
     */
    protected void handleKeyword(Token node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_KEYWORD));
    } // handleKeyword

    /**
     * Handle a literal token ('false', 'true', 'null').
     *
     * @param  node  token representing a literal.
     */
    protected void handleLiteral(Token node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_LITERAL));
    } // handleLiteral

    //
    // These are the AnalysisAdapter methods that we override.
    //

    /**
     *
     * @param  node  decimal literal.
     */
    @Override
	public void caseTDecimalIntegerLiteral(TDecimalIntegerLiteral node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_NUMBER));
    }

    /**
     *
     * @param  node  hexadecimal literal.
     */
    @Override
	public void caseTHexIntegerLiteral(THexIntegerLiteral node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_NUMBER));
    }

    /**
     *
     * @param  node  octal literal.
     */
    @Override
	public void caseTOctalIntegerLiteral(TOctalIntegerLiteral node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_NUMBER));
    }

    /**
     *
     * @param  node  floating literal.
     */
    @Override
	public void caseTFloatingPointLiteral(TFloatingPointLiteral node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_NUMBER));
    }

    /**
     *
     * @param  node  character literal.
     */
    @Override
	public void caseTCharacterLiteral(TCharacterLiteral node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_CHARACTER));
    }

    /**
     *
     * @param  node  string literal.
     */
    @Override
	public void caseTStringLiteral(TStringLiteral node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_STRING));
    }

    /**
     *
     * @param  node  comment.
     */
    @Override
	public void caseTTraditionalComment(TTraditionalComment node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_COMMENT));
    }

    /**
     *
     * @param  node  comment.
     */
    @Override
	public void caseTDocumentationComment(TDocumentationComment node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_COMMENT));
    }

    /**
     *
     * @param  node  comment.
     */
    @Override
	public void caseTEndOfLineComment(TEndOfLineComment node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_COMMENT));
    }

    /**
     *
     * @param  node  identifier.
     */
    @Override
	public void caseTIdentifier(TIdentifier node) {
        int offset = getOffset(node);
        tokenList.insert(offset, new JavaTokenInfo(
            offset, node.getText().length(),
            JavaTokenInfo.TOKEN_IDENTIFIER));
    }

    /**
     *
     * @param  node  assert keyword.
     */
    @Override
	public void caseTAssert(TAssert node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  abstract keyword.
     */
    @Override
	public void caseTAbstract(TAbstract node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  boolean keyword.
     */
    @Override
	public void caseTBoolean(TBoolean node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  break keyword.
     */
    @Override
	public void caseTBreak(TBreak node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  byte keyword.
     */
    @Override
	public void caseTByte(TByte node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  case keyword.
     */
    @Override
	public void caseTCase(TCase node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  catch keyword.
     */
    @Override
	public void caseTCatch(TCatch node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  char keyword.
     */
    @Override
	public void caseTChar(TChar node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  class keyword.
     */
    @Override
	public void caseTClass(TClass node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  const keyword.
     */
    @Override
	public void caseTConst(TConst node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  continue keyword.
     */
    @Override
	public void caseTContinue(TContinue node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  default keyword.
     */
    @Override
	public void caseTDefault(TDefault node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  do keyword.
     */
    @Override
	public void caseTDo(TDo node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  double keyword.
     */
    @Override
	public void caseTDouble(TDouble node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  else keyword.
     */
    @Override
	public void caseTElse(TElse node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  extends keyword.
     */
    @Override
	public void caseTExtends(TExtends node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  final keyword.
     */
    @Override
	public void caseTFinal(TFinal node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  finally keyword.
     */
    @Override
	public void caseTFinally(TFinally node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  float keyword.
     */
    @Override
	public void caseTFloat(TFloat node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  for keyword.
     */
    @Override
	public void caseTFor(TFor node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  goto keyword.
     */
    @Override
	public void caseTGoto(TGoto node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  if keyword.
     */
    @Override
	public void caseTIf(TIf node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  implements keyword.
     */
    @Override
	public void caseTImplements(TImplements node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  import keyword.
     */
    @Override
	public void caseTImport(TImport node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  instanceof keyword.
     */
    @Override
	public void caseTInstanceof(TInstanceof node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  int keyword.
     */
    @Override
	public void caseTInt(TInt node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  interface keyword.
     */
    @Override
	public void caseTInterface(TInterface node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  long keyword.
     */
    @Override
	public void caseTLong(TLong node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  native keyword.
     */
    @Override
	public void caseTNative(TNative node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  new keyword.
     */
    @Override
	public void caseTNew(TNew node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  package keyword.
     */
    @Override
	public void caseTPackage(TPackage node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  private keyword.
     */
    @Override
	public void caseTPrivate(TPrivate node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  protected keyword.
     */
    @Override
	public void caseTProtected(TProtected node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  public keyword.
     */
    @Override
	public void caseTPublic(TPublic node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  return keyword.
     */
    @Override
	public void caseTReturn(TReturn node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  short keyword.
     */
    @Override
	public void caseTShort(TShort node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  static keyword.
     */
    @Override
	public void caseTStatic(TStatic node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  strictfp keyword.
     */
    @Override
	public void caseTStrictfp(TStrictfp node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  super keyword.
     */
    @Override
	public void caseTSuper(TSuper node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  switch keyword.
     */
    @Override
	public void caseTSwitch(TSwitch node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  synchronized keyword.
     */
    @Override
	public void caseTSynchronized(TSynchronized node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  this keyword.
     */
    @Override
	public void caseTThis(TThis node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  throw keyword.
     */
    @Override
	public void caseTThrow(TThrow node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  throws keyword.
     */
    @Override
	public void caseTThrows(TThrows node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  transient keyword.
     */
    @Override
	public void caseTTransient(TTransient node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  try keyword.
     */
    @Override
	public void caseTTry(TTry node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  void keyword.
     */
    @Override
	public void caseTVoid(TVoid node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  volatile keyword.
     */
    @Override
	public void caseTVolatile(TVolatile node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  while keyword.
     */
    @Override
	public void caseTWhile(TWhile node) {
        handleKeyword(node);
    }

    /**
     *
     * @param  node  true literal.
     */
    @Override
	public void caseTTrue(TTrue node) {
        handleLiteral(node);
    }

    /**
     *
     * @param  node  false literal.
     */
    @Override
	public void caseTFalse(TFalse node) {
        handleLiteral(node);
    }

    /**
     *
     * @param  node  null literal.
     */
    @Override
	public void caseTNull(TNull node) {
        handleLiteral(node);
    }
} // JavaScanner
