package es.ucm.fdi.business.data;

import java.util.List;

public class AnswerPOJO {
	private List<Object> answer;

	public AnswerPOJO(List<Object> answer) {
		this.answer = answer;
	}

	public List<Object> getAnswer() {
		return answer;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof AnswerPOJO
				&& answer.equals(((AnswerPOJO) o).answer);
	}
}
