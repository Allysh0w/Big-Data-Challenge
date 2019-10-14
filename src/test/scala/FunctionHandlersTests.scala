
import org.scalatest.FlatSpec

class FunctionHandlersTests extends FlatSpec {

  import com.bigdata.challenge.handlers.SimilarityFunctionsHandler._

  "The filter " should "return true case the UserRatingPair is different" in {
    val input: UserRatingPair = (1, ((1, 1.0), (1, 2.0)))
    assert(filterDuplicate(input).equals(true))
  }

  "The filter " should "return true case the UserRatingPair is the same" in {
    val input: UserRatingPair = (1, ((1, 1.0), (1, 1.0)))
    assert(filterDuplicate(input).equals(false))
  }

  "The function mapPair " should "group by url with rates" in {
    val input: UserRatingPair = (1, ((1, 2.0), (2, 3.0)))
    assert(mappPair(input).equals(((1, 2), (2.0, 3.0))))
  }

  "The function cosineSimilarityCalc " should "compute the similarities" in {
    val input: Iterable[RatingPair] = Iterable((1.0, 1.0))
    assert(cosineSimilarityCalc(input).equals(1.0, 1))
  }



}
