package tw.com.jasper.mp_chart.utils

object Const {
    object LineChart {
        const val CIRCLE_RADIUS = 5f
        const val CIRCLE_HOLE_RADIUS = 4f
    }

    object Common {
        object FakeDataSource {
            const val ENTRY_LIST_COUNT = 20
        }

        object TextStyle {
            const val TEXT_SIZE = 10f
        }

        object LineStyle {
            const val LINE_WIDTH = 0.5f
        }

        object DashedLineStyle {
            const val DASHED_LINE_LENGTH = 3f
            const val DASHED_SPACE_LENGTH = 1f
            const val DASHED_PHASE = 0f
        }

        object AxisStyle {
            const val CHART_OFFSET_0 = 0f
            const val CHART_OFFSET_5 = 5f
        }

        object DescStyle {
            const val TESTING_DESC = "這是測試的描述內容"
            const val TESTING_NO_DATA_DESC = "目前沒有資料"
        }
    }
}